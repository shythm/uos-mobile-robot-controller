package uos.teamkernel.map;

import java.awt.Point;

import uos.teamkernel.common.IModelObservable;
import uos.teamkernel.common.Spot;

public interface IMapModel extends IModelObservable {

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

}
