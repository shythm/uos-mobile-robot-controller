package uos.teamkernel.sim;

import uos.teamkernel.common.Point;
import java.util.ArrayList;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.ModelObserver;

public class Map implements MapModel {

    private Spot[][] map;
    private ArrayList<ModelObserver> observers;

    public Map(int w, int h) {
        map = new Spot[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                map[i][j] = Spot.NONE;
        observers = new ArrayList<ModelObserver>();
    }

    public Spot getSpot(int x, int y) {
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            return Spot.NONE;
        }
        return map[x][y];
    }

    public Spot getSpot(Point position) {
        return getSpot(position.getLocationX(), position.getLocationY());
    }

    public void setSpot(Point position, Spot spot) {
        int x = position.getLocationX();
        int y = position.getLocationY();
        // check if the position is valid
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            System.out.println(String.format("setSpot: Invalid Position (%d %d)", x, y));
            return;
        }
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
