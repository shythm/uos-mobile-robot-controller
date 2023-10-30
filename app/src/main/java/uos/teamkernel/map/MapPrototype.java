package uos.teamkernel.map;

import java.awt.Point;
import java.util.ArrayList;
import uos.teamkernel.common.IModelObserver;
import uos.teamkernel.common.Spot;

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
