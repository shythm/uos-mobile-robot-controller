package uos.teamkernel.prototype;

import java.util.ArrayList;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.ModelObserver;

public class MapPrototype implements MapModel {

    private Spot[][] map;
    private ArrayList<ModelObserver> observers;

    public MapPrototype() {
        map = new Spot[10][10];
        observers = new ArrayList<ModelObserver>();
    }

    public Spot getSpot(Point position) {
        return map[position.getLocationX()][position.getLocationY()];
    }

    public Spot getSpot(int x, int y) {
        return map[x][y];
    }

    public void setSpot(Point position, Spot spot) {
        map[position.getLocationX()][position.getLocationY()] = spot;
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
        for (ModelObserver observer : observers) {
            observer.modelChanged(this);
        }
    }

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

}
