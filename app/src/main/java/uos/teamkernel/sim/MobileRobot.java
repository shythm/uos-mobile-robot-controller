package uos.teamkernel.sim;

import java.util.ArrayList;
import teamkernel.sim.common.Spot;
import teamkernel.sim.common.Point;
import teamkernel.sim.common.Direction;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.model.ModelObserver;

public class MobileRobot implements MobileRobotModel {

    private Direction direction;
    private Point position;
    private ArrayList<ModelObserver> observers;
    private Map referenceMap;

    public MobileRobot(Map referenceMap, Point startPoint) {
        this.referenceMap = referenceMap;

        direction = Direction.SOUTH; // initial direction is south
        position = startPoint; // initial position

        observers = new ArrayList<ModelObserver>();
    }

    public Direction turn() {
        direction = direction.clockwise();
        notifyObservers(); // notify observers that the state of model has changed
        return direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Point predictNextPosition(Direction d) {
        Point nextPosition = switch (d) {
        case NORTH -> new Point(position.x, position.y + 1);
        case EAST -> new Point(position.x + 1, position.y);
        case SOUTH -> new Point(position.x, position.y - 1);
        case WEST -> new Point(position.x - 1, position.y);
        default -> new Point(-1, -1);
        };

        return nextPosition;
    }

    public Point move() {
        // predict next position
        Point ret = predictNextPosition(direction);

        // check if the position is valid
        if (!isInsideMap(ret)) {
            System.out.println("move: Invalid Position");
            ret = this.position;
        }

        position = ret;
        notifyObservers(); // notify observers that the state of model has changed
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean senseHazard() {
        Point nextPosition = predictNextPosition(direction);
        return referenceMap.getSpot(nextPosition) == Spot.HAZARD;
    }

    public boolean[] senseColorBlobs() {
        boolean[] colorBlobExistences = new boolean[4];

        for (int i = 0; i < 4; i++) {
            if (referenceMap.getSpot(predictNextPosition(Direction.fromInteger(i))) == Spot.COLOR_BLOB) {
                colorBlobExistences[i] = true;
            }
        }

        return colorBlobExistences;
    }

    public boolean isInsideMap(Point p) {
        int x = p.x, y = p.y;
        return (0 <= x) && (x < referenceMap.getWidth()) && (0 <= y) && (y < referenceMap.getHeight());
    }

    /* below is for observer pattern */

    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.modelChanged(this);
        }
    }

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

}
