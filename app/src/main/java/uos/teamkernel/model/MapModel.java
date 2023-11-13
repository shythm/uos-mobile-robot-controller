package uos.teamkernel.model;

import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;

public interface MapModel extends ModelObservable {

    /**
     * Get the type of spot at position
     * 
     * @param position Point(x, y)
     * @return The type of spot at position
     */
    Spot getSpot(Point position);

    /**
     * Set the type of spot at position
     * 
     * @param position Point(x, y)
     * @param spot     The type of spot at position
     */
    void setSpot(Point position, Spot spot);

    /**
     * Get the width of the map
     * 
     * @return The width of the map
     */
    int getWidth();

    /**
     * Get the height of the map
     * 
     * @return The height of the map
     */
    int getHeight();

}
