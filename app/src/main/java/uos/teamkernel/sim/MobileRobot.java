package uos.teamkernel.sim;

import java.util.ArrayList;
import uos.teamkernel.common.Spot;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Direction;
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

    private Point predictNextPosition() {
        Point nextPosition = switch (direction) {
        case NORTH -> new Point(position.x, position.y - 1);
        case EAST -> new Point(position.x + 1, position.y);
        case SOUTH -> new Point(position.x, position.y + 1);
        case WEST -> new Point(position.x - 1, position.y);
        default -> new Point(-1, -1);
        };

        return nextPosition;
    }

    public Point move() {
        position = predictNextPosition();
        notifyObservers(); // notify observers that the state of model has changed
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean senseHazard() {
        Point nextPosition = predictNextPosition();
        return referenceMap.getSpot(nextPosition) == Spot.HAZARD;
    }

    public boolean[] senseColorBlobs() {
        boolean[] ret = new boolean[4];

        if (referenceMap.getSpot(position.x, position.y - 1) == Spot.COLOR_BLOB) {
            ret[Direction.NORTH.ordinal()] = true;
        }
        if (referenceMap.getSpot(position.x + 1, position.y) == Spot.COLOR_BLOB) {
            ret[Direction.EAST.ordinal()] = true;
        }
        if (referenceMap.getSpot(position.x, position.y + 1) == Spot.COLOR_BLOB) {
            ret[Direction.SOUTH.ordinal()] = true;
        }
        if (referenceMap.getSpot(position.x - 1, position.y) == Spot.COLOR_BLOB) {
            ret[Direction.WEST.ordinal()] = true;
        }

        return ret;
    }

    public boolean isInsideMap(Point p) {
        int x = p.getLocationX(), y = p.getLocationY();
        return (0 <= x) && (x <= referenceMap.getWidth()) && (0 <= y) && (y <= referenceMap.getHeight());
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
