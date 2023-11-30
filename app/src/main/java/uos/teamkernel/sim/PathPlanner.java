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
            // System.out.println(curPosition);
            if (curPosition.equals(EndPosition)) {
                path = new LinkedList<>();
                while (true) {
                    if (route[curPosition.x][curPosition.y] == null) {
                        break;
                    }
                    path.addFirst(curPosition);
                    curPosition = route[curPosition.x][curPosition.y];
                }
                // System.out.println(path);
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
    private Direction CalculateDirection(Point startPoint, Point endPoint) {
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
        Point currPosition = mobileRobot.getPosition();
        System.out.println("[PATH] Current " + currPosition);

        boolean needToPlan = (path == null) || // 초기 상태
                (map.getSpot(currPosition) == Spot.PREDEFINED_SPOT_VISITED) || // 방문 지점에 도착한 경우(다음 방문 지점 탐색 필요)
                (map.getSpot(nextPosition) == Spot.HAZARD); // 로봇이 다음으로 이동할 위치가 위험 지역인 경우

        if (needToPlan) {
            Point destPoint = getClosestDestinationPoint(mobileRobot, map);
            if (destPoint == null) {
                System.out.println("[PATH] No destination");
                return Direction.UNKNOWN;
            }
            System.out.println("[PATH] Destination " + destPoint);
            bfs(mobileRobot, map, currPosition, destPoint);
            nextPosition = getNextPoint();
        }

        // 로봇이 회전을 끝마치고 다음 위치로 이동했을 경우
        if (nextPosition.equals(currPosition)) {
            nextPosition = getNextPoint();
        }

        System.out.println("[PATH] Next " + nextPosition);
        return CalculateDirection(currPosition, nextPosition);
    }
}
