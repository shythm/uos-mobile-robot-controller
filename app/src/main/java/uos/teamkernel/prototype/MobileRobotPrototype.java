package uos.teamkernel.prototype;

import java.awt.Point;
import java.util.ArrayList;
import uos.teamkernel.common.Direction;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.model.ModelObserver;

public class MobileRobotPrototype implements MobileRobotModel {

    private Direction direction;
    private Point position;
    private ArrayList<ModelObserver> observers;

    public MobileRobotPrototype() {
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
        position = new Point(position.x + 1, position.y + 1);
        notifyObservers(); // notify observers that the state of model has changed
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean senseHazard() {
        return false;
    }

    public boolean senseColorBlob() {
        return false;
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