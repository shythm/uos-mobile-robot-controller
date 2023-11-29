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

    public PathPlanner() {

    }

    /**
     * Returns the closest predefined spot to the robot's current point.
     * 
     * @return a closest predefined spot
     */
    private Point getClosestDestinationPoint(MobileRobot mobileRobot, MapModel robotMap) {
        Point nearestPoint = new Point(100, 100);
        Point mobileRobotPosition = mobileRobot.getPosition();

        for (int i = 0; i < robotMap.getWidth(); i++) {
            for (int j = 0; j < robotMap.getHeight(); j++) {
                if (robotMap.getSpot(i, j) == Spot.PREDEFINED_SPOT) {
                    Point curPoint = new Point(i, j);
                    if (mobileRobotPosition.getDistance(nearestPoint) > mobileRobotPosition.getDistance(curPoint)) {
                        nearestPoint = curPoint;
                    }
                }
            }
        }
        return nearestPoint;
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
                    path.addFirst(curPosition);
                    System.out.println(path);
                    if (route[curPosition.x][curPosition.y] == null) {
                        break;
                    }
                    curPosition = route[curPosition.x][curPosition.y];
                }
                return;
            }
            Point[] AdjPointList = curPosition.getAdjPointList();
            for (Point nextPoint : AdjPointList) {
                if (mobileRobot.isInsideMap(nextPoint) && !isVisited[nextPoint.x][nextPoint.y]
                        && !robotMap.getSpot(nextPoint).isEqual(Spot.HAZARD)) {
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
    public Direction call(MobileRobotModel mobileRobot, MapModel map) {
        Point curPosition = mobileRobot.getPosition();
        System.out.println("Current : " + curPosition);
        if (map.getSpot(curPosition.x, curPosition.y).equals(Spot.VISITED_PREDEFINED_SPOT) | path == null) {
            bfs((MobileRobot)mobileRobot, map, curPosition, getClosestDestinationPoint((MobileRobot)mobileRobot, map));
            nextPosition = getNextPoint();
            System.out.println("Next : " + nextPosition);
        }
        if (nextPosition.equals(curPosition)) {
            nextPosition = getNextPoint();
            System.out.println("Next : " + nextPosition);
        }
        Direction dir = CalculateDirection(curPosition, nextPosition);
        return dir;
    }
}
