package uos.teamkernel.sim;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import uos.teamkernel.common.Direction;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

public class PathPlanner implements SimAddOn<Direction> {
    private Stack<Point> memorizedPath;

    public PathPlanner() {
        memorizedPath = new Stack<Point>();
    }

    /**
     * Returns the closest predefined spot to the robot's current point.
     * 
     * @return a closest predefined spot
     */
    private Point getClosestPoint(MobileRobot mobileRobot, MapModel map) {
        Point nearestPoint = new Point(100, 100);
        Point mobileRobotPosition = mobileRobot.getPosition();

        for (int i = 0; i < map.getWidth(); i++) {
                if (map.getSpot(i, j).isEqual(Spot.PREDEFINED_SPOT)) {
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
     * Returns the shortest path from the start point to the end point.
     * 
     * @return the shortest path
     */
    private Stack<Point> bfs(MobileRobot mobileRobot, MapModel map, Point startPosition, Point EndPosition) {
        Queue<Point> queue = new LinkedList<>();
        Stack<Point> finalRoute = new Stack<>();
        Set<Point> visited = new HashSet<>();
        HashMap<Point, Point> backTrackPointDict = new HashMap<>();
        Point curPosition;
        queue.add(startPosition);
        visited.add(startPosition);

        while (!queue.isEmpty()) {
            curPosition = queue.peek();
            queue.poll();

            Point[] AdjPointList = curPosition.getAdjPointList();
            for (Point nextPoint : AdjPointList) {
                if (mobileRobot.isInsideMap(nextPoint) && !visited.contains(nextPoint)) {
                    queue.add(nextPoint);
                    backTrackPointDict.put(nextPoint, curPosition);
                    visited.add(nextPoint);
                }
            }
        }
        for (Point p = EndPosition; p != startPosition; p = backTrackPointDict.get(p))
            finalRoute.add(p);

        return finalRoute;

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
        case 1 -> Direction.WEST;
        case 0 -> (yDiff == 1) ? Direction.NORTH : Direction.SOUTH;
        case -1 -> Direction.EAST;
        default -> Direction.UNKNOWN;
        };
        return dir;
    }

    /**
     * Update the route when the map is changed.
     */
    private void updateRoute(MobileRobot mobileRobot, MapModel map) {
        Point startPoint = mobileRobot.getPosition();
        Point endPoint = this.getClosestPoint(mobileRobot, map);

        memorizedPath = bfs(mobileRobot, map, startPoint, endPoint);
    }

    /**
     * Returns the direction in which the robot will move next
     * 
     * @return a direction
     */
    public Direction call(MobileRobotModel mobileRobot, MapModel map) {
        if (memorizedPath.isEmpty()) {
            this.updateRoute((MobileRobot)mobileRobot, map);
        }
        memorizedPath.poll();

        Point curPosition = mobileRobot.getPosition();
        Point nextPosition = memorizedPath.pop();
        Direction dir = CalculateDirection(curPosition, nextPosition);
        return dir;
    }

}
