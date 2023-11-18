package uos.teamkernel.sim;

import java.util.ArrayList;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Direction;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.model.ModelObserver;

public class MobileRobot implements MobileRobotModel {

    private Direction direction;
    private Point position;
    private ArrayList<ModelObserver> observers;
    static double probability = 0.05;

    public MobileRobot() {
        direction = Direction.NORTH;
        position = new Point(0, 0);
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

    public Point move() {
        position = new Point(position.getLocationX() + 1, position.getLocationY() + 1);
        notifyObservers(); // notify observers that the state of model has changed
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean senseHazard() {
        if (Math.random() < probability)
            return true;
        else
            return false;
    }

    public Direction senseColorBlob() {

        for (int i = 0; i < 4; i++) {
            if (Math.random() < probability) {
                return Direction.fromInteger(i);
            }
        }
        return Direction.UNKNOWN;
    }

    public boolean isInsideMap(Point p) {
        int x = p.getLocationX(), y = p.getLocationY();
        return (0 <= x) && (x <= map.getWidth()) && (0 <= y) && (y <= map.getHeight());
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
