package uos.teamkernel.sim;

import uos.teamkernel.common.Point;
import java.util.ArrayList;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.ModelObserver;

public class Map implements MapModel {

    private Spot[][] map;
    private ArrayList<ModelObserver> observers;

    public Map() {
        map = new Spot[10][10];
        initMap(10, 10);
        observers = new ArrayList<ModelObserver>();
    }

    public Map(int w, int h) {
        map = new Spot[w + 1][h + 1]; // add 1 for boundary
        initMap(w + 1, h + 1);
        observers = new ArrayList<ModelObserver>();
    }

    public void initMap(int w, int h) {
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                map[i][j] = Spot.NONE;
    }

    public Spot getSpot(Point position) {
        int x = position.getLocationX();
        int y = position.getLocationY();

        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            return Spot.NONE;
        }
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
