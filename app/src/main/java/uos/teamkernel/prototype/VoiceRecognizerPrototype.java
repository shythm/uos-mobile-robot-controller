package uos.teamkernel.prototype;

import java.awt.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.map.IMapModel;
import uos.teamkernel.robot.IMobileRobotModel;
import uos.teamkernel.sim.core.ISimAddOn;

public class VoiceRecognizerPrototype implements ISimAddOn<Void> {

    public Void call(IMobileRobotModel mobileRobot, IMapModel map) {
        map.setSpot(new Point(1, 1), Spot.HAZARD);
        return null;
    }

}
