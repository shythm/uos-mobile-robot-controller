package uos.teamkernel.sim.core;

import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

public interface SimAddOn<T> {

    /**
     * Do additional control to the simulator and return the target direction to
     * move. It can just throw the given next direction as it is.
     * 
     * @param sim  the simulator controller(caller)
     * @param next the direction to move in this step
     * @return new direction to move in this step
     */
    T call(MobileRobotModel mobileRobot, MapModel map);

}