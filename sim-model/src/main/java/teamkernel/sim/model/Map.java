package teamkernel.sim.model;

import java.util.ArrayList;
import teamkernel.sim.common.Point;
import teamkernel.sim.common.Spot;

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
        return getSpot(position.x, position.y);
    }

    public void setSpot(Point position, Spot spot) {
        if (position == null) {
            System.out.println("setSpot: Invalid Position (null)");
            return;
        }

        int x = position.x;
        int y = position.y;
        // check if the position is valid
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            System.out.println(String.format("setSpot: Invalid Position (%d %d)", x, y));
            return;
        }
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
        for (ModelObserver observer : observers) {
            observer.modelChanged(this);
        }
    }

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

}
