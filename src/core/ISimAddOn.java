import java.awt.Point;
import core.SimController;

interface ISimAddOn {

    /**
     * when the add-on is registered to the simulator, it will be called
     */
    void init(SimController sim);

    /**
     * do additional function to the robot and return the target position
     * it can be just throw the target position back
     */
    Point step(SimController sim, Point targetPos);

}