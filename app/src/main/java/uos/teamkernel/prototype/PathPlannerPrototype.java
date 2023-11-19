package uos.teamkernel.prototype;

import uos.teamkernel.common.Direction;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.sim.SimAddOn;

public class PathPlannerPrototype implements SimAddOn<Direction> {

    public Direction call(MobileRobotModel mobileRobot, MapModel map) {
        return mobileRobot.getDirection();
    }

}
