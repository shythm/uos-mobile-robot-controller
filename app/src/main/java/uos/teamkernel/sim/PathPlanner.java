package uos.teamkernel.sim;

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

    public PathPlanner() {

    }

    /**
     * Returns the closest predefined spot to the robot's current point.
     * 
     * @return a closest predefined spot
     */
    private Point getClosestPoint(MobileRobot mobileRobot, MapModel robotMap) {
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
    private Point bfs(MobileRobot mobileRobot, MapModel robotMap, Point startPosition, Point EndPosition) {
        Queue<Point> queue = new LinkedList<>();
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
                if (mobileRobot.isInsideMap(nextPoint) && !visited.contains(nextPoint)
                        && !robotMap.getSpot(nextPoint).isEqual(Spot.HAZARD)) {
                    queue.add(nextPoint);
                    backTrackPointDict.put(nextPoint, curPosition);
                    visited.add(nextPoint);
                }
            }
        }
        Point p;
        for (p = EndPosition; backTrackPointDict.get(p) != startPosition;)
            p = backTrackPointDict.get(p);
        return p;
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
    private Point getNextPoint(MobileRobot mobileRobot, MapModel map) {
        Point startPoint = mobileRobot.getPosition();
        Point endPoint = this.getClosestPoint((MobileRobot)mobileRobot, map);
        Point nextPoint = bfs((MobileRobot)mobileRobot, map, startPoint, endPoint);
        return nextPoint;
    }

    /**
     * Returns the direction in which the robot will move next
     * 
     * @return a direction
     */
    public Direction call(MobileRobotModel mobileRobot, MapModel map) {
        Point curPosition = mobileRobot.getPosition();
        Point nextPosition = getNextPoint((MobileRobot)mobileRobot, map);
        Direction dir = CalculateDirection(curPosition, nextPosition);
        return dir;
    }

}
