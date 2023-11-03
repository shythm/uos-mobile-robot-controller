package uos.teamkernel.prototype;

import java.awt.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.sim.SimAddOn;

public class VoiceRecognizerPrototype implements SimAddOn<Void> {

    public Void call(MobileRobotModel mobileRobot, MapModel map) {
        map.setSpot(new Point(1, 1), Spot.HAZARD);
        return null;
    }

}
