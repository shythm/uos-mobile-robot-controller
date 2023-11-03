package uos.teamkernel.sim;

import java.awt.Point;
import uos.teamkernel.common.Direction;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.view.SimMainView;

public class SimController {

    private MobileRobotModel mobileRobot;
    private MapModel map;
    private SimMainView mainView;

    private SimAddOn<Direction> pathPlanner;
    private SimAddOn<Void> voiceRecognizer;

    public SimController(MobileRobotModel mobileRobot, MapModel map, SimMainView mainView,
            SimAddOn<Direction> pathPlanner, SimAddOn<Void> voiceRecognizer) {
        this.mobileRobot = mobileRobot;
        this.map = map;

        this.pathPlanner = pathPlanner;
        this.voiceRecognizer = voiceRecognizer;

        this.mainView = mainView;
        this.mainView.addStepButtonListener(e -> step());
        this.mainView.addVoiceButtonListener(e -> voice());
    }

    /**
     * A handler of the step button
     */
    private void step() {
        Direction curr = mobileRobot.getDirection();
        Direction next = pathPlanner.call(mobileRobot, map);

        if (next == Direction.UNKNOWN) {
            System.out.println("[STEP] Unknown direction to move");
            return;
        }

        // move the robot
        if (curr != next) {
            // turn to next direction
            Direction dir = mobileRobot.turn();
            System.out.println("[STEP] Turned to " + dir);
        } else {
            // curr and next is same! the robot can move toward the next direction
            Point pos = mobileRobot.move();
            System.out.println("[STEP] Moved to " + pos);
        }

        // check if there is a hazard or color blob
        if (mobileRobot.senseHazard()) {
            System.out.println("[SENSE] Hazard detected");
            // TODO: update map
        } else if (mobileRobot.senseColorBlob()) {
            System.out.println("[SENSE] Color blob detected");
            // TODO: update map
        }
    }

    /**
     * A handler of the voice button
     */
    private void voice() {
        voiceRecognizer.call(mobileRobot, map);
    }

}