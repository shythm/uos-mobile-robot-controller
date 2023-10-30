package uos.teamkernel.prototype;

import uos.teamkernel.common.Direction;
import uos.teamkernel.robot.IMobileRobotModel;
import uos.teamkernel.map.IMapModel;
import uos.teamkernel.sim.core.ISimAddOn;

public class PathPlannerPrototype implements ISimAddOn<Direction> {

    public Direction call(IMobileRobotModel mobileRobot, IMapModel map) {
        return mobileRobot.getDirection();
    }

}
