package uos.teamkernel.sim;

import java.util.Queue;

import java.util.LinkedList;

import java.util.Deque;
import uos.teamkernel.common.Direction;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

public class PathPlanner implements SimAddOn<Direction> {
    Deque<Point> path;
    Point nextPosition;

    /**
     * Returns the closest unvisited predefined spot to the robot's current point.
     * 
     * @return if there is a closest predefined spot return the point, otherwise
     *         return null
     */
    private Point getClosestDestinationPoint(MobileRobot mobileRobot, MapModel robotMap) {
        Point closestPoint = null;
        boolean isFound = false;
        Point mobileRobotPosition = mobileRobot.getPosition();

        for (int i = 0; i < robotMap.getWidth(); i++) {
            for (int j = 0; j < robotMap.getHeight(); j++) {
                Spot spot = robotMap.getSpot(i, j);
                // if the spot is a predefined spot and not visited
                if (spot == Spot.PREDEFINED_SPOT) {
                    Point curPoint = new Point(i, j);
                    // if the closest point is not found yet or the current point is closer to the
                    // robot
                    if (!isFound) {
                        closestPoint = curPoint;
                        isFound = true;
                    } else if (mobileRobotPosition.getDistance(closestPoint) > mobileRobotPosition
                            .getDistance(curPoint)) {
                        closestPoint = curPoint;
                    }
                }
            }
        }

        return closestPoint;
    }

    /**
     * Returns the first point on the shortest path from the starting point to the
     * ending point
     * 
     * @return the first point on the shortest path
     */
    private void bfs(MobileRobot mobileRobot, MapModel robotMap, Point startPosition, Point EndPosition) {
        Queue<Point> queue = new LinkedList<>();
        Point[][] route = new Point[robotMap.getWidth()][robotMap.getHeight()];
        boolean[][] isVisited = new boolean[robotMap.getWidth()][robotMap.getHeight()];
        Point curPosition;
        queue.add(startPosition);
        isVisited[startPosition.x][startPosition.y] = true;

        while (!queue.isEmpty()) {
            curPosition = queue.poll();
            System.out.println(curPosition);
            if (curPosition.equals(EndPosition)) {
                path = new LinkedList<>();
                while (true) {
                    if (route[curPosition.x][curPosition.y] == null) {
                        break;
                    }
                    path.addFirst(curPosition);
                    curPosition = route[curPosition.x][curPosition.y];
                }
                System.out.println(path);
                return;
            }
            Point[] AdjPointList = curPosition.getAdjPointList();
            for (Point nextPoint : AdjPointList) {
                if (mobileRobot.isInsideMap(nextPoint) && !isVisited[nextPoint.x][nextPoint.y]
                        && robotMap.getSpot(nextPoint) != Spot.HAZARD) {
                    queue.add(nextPoint);
                    route[nextPoint.x][nextPoint.y] = curPosition;
                    isVisited[nextPoint.x][nextPoint.y] = true;
                }
            }
        }
    }

    /**
     * Returns the direction from a starting point to an adjacent end point
     * 
     * @return a direction
     */
    public Direction CalculateDirection(Point startPoint, Point endPoint) {
        int xDiff = endPoint.x - startPoint.x;
        int yDiff = endPoint.y - startPoint.y;

        Direction dir = switch (xDiff) {
        case 1 -> Direction.EAST;
        case 0 -> (yDiff == 1) ? Direction.NORTH : Direction.SOUTH;
        case -1 -> Direction.WEST;
        default -> Direction.UNKNOWN;
        };
        return dir;
    }

    /**
     * Returns the next point to reach to the closest destination from the robot's
     * current position.
     * 
     * @return the next point
     */
    private Point getNextPoint() {
        Point nextPoint = path.poll();
        return nextPoint;
    }

    /**
     * Returns the direction in which the robot will move next
     * 
     * @return a direction
     */
    public Direction call(MobileRobotModel mobileRobotModel, MapModel map) {
        MobileRobot mobileRobot = (MobileRobot)mobileRobotModel;

        Point curPosition = mobileRobot.getPosition();
        System.out.println("[PATH] Current " + curPosition);

        Spot currentSpot = map.getSpot(curPosition);

        // if the first time to path plan or the robot is on a predefined spot
        if (path == null || currentSpot == Spot.PREDEFINED_SPOT_VISITED) {
            System.out.println(getClosestDestinationPoint(mobileRobot, map));
            bfs(mobileRobot, map, curPosition, getClosestDestinationPoint(mobileRobot, map));
            nextPosition = getNextPoint();
            System.out.println("Next : " + nextPosition);
        }
        if (nextPosition.equals(curPosition)) {
            nextPosition = getNextPoint();
            System.out.println("Next : " + nextPosition);
        }
        if (map.getSpot(nextPosition.x, nextPosition.y).equals(Spot.HAZARD)) {
            bfs(mobileRobot, map, curPosition, getClosestDestinationPoint(mobileRobot, map));
            nextPosition = getNextPoint();
            System.out.println("Next : " + nextPosition);
        }
        Direction dir = CalculateDirection(curPosition, nextPosition);
        return dir;
    }
}
