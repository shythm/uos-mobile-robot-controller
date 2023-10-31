package uos.teamkernel.prototype;

import java.awt.Point;
import java.util.ArrayList;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.IMapModel;
import uos.teamkernel.model.IModelObserver;

public class MapPrototype implements IMapModel {

    private Spot[][] map;
    private ArrayList<IModelObserver> observers;

    public MapPrototype() {
        map = new Spot[10][10];
        observers = new ArrayList<IModelObserver>();
    }

    public Spot getSpot(Point position) {
        return map[position.x][position.y];
    }

    public void setSpot(Point position, Spot spot) {
        map[position.x][position.y] = spot;
        notifyObservers(); // notify observers that the state of model has changed
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }

    /* below is for observer pattern */

    private void notifyObservers() {
        for (IModelObserver observer : observers) {
            observer.modelChanged(this);
        }
    }

    public void addObserver(IModelObserver observer) {
        observers.add(observer);
    }

}
