import java.awt.Point;

interface IMapModel {
    // add hazard at position
    void addHazard(Point position);

    // add color blob at position
    void addColorBlob(Point position);

    /** 
     * get the spot at position 
     * (it will return 0 if there is no hazard or color blob at position)
     */
    int getSpot(Point position);
}