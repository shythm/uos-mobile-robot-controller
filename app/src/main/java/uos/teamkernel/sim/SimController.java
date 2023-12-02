package uos.teamkernel.sim;

import java.util.Timer;
import java.util.TimerTask;

import teamkernel.sim.common.Direction;
import teamkernel.sim.common.Point;
import teamkernel.sim.common.Spot;
import teamkernel.sim.model.Map;
import teamkernel.sim.model.MobileRobot;
import teamkernel.sim.view.SimMainView;

public class SimController {

    private MobileRobot mobileRobot;
    private Map map;
    private SimMainView mainView;

    private PathPlanner pathPlanner;
    private VoiceRecognizer voiceRecognizer;

    private int stepCount = 0;
    private Timer autoStepTimer = null;

    public SimController(MobileRobot mobileRobot, Map map, SimMainView mainView, PathPlanner pathPlanner,
            VoiceRecognizer voiceRecognizer) {
        this.mobileRobot = mobileRobot;
        this.map = map;

        this.pathPlanner = pathPlanner;
        this.voiceRecognizer = voiceRecognizer;

        this.mainView = mainView;
        this.mainView.addStepButtonListener(e -> step());
        this.mainView.addVoiceButtonListener(e -> voice());
        this.mainView.addAutoManualButtonListener(e -> auto());
    }

    private void sense() {
        Point pos = mobileRobot.getPosition();
        Direction dir = mobileRobot.getDirection();

        // check if there is a hazard or color blob
        boolean hazardExistence = mobileRobot.senseHazard();
        if (hazardExistence) {
            Point hazard = mobileRobot.predictNextPosition(dir);
            System.out.println("[SENSE] Hazard detected at " + hazard);
            map.setSpot(hazard, Spot.HAZARD);
        }

        // check if there are color blobs
        boolean[] colorBlobExistences = mobileRobot.senseColorBlobs();
        for (int i = 0; i < 4; i++) {
            if (colorBlobExistences[i]) {
                Point colorBlob = mobileRobot.predictNextPosition(Direction.fromInteger(i));
                System.out.println("[SENSE] Color blob detected at " + colorBlob);
                map.setSpot(colorBlob, Spot.COLOR_BLOB);
            }
        }

        // check if the robot is on a predefined spot
        if (map.getSpot(pos) == Spot.PREDEFINED_SPOT) {
            map.setSpot(pos, Spot.PREDEFINED_SPOT_VISITED);
        }
    }

    /**
     * A handler of the step button
     */
    private void step() {
        if (stepCount == 0) {
            sense();
        } else {
            System.out.println("[STEP] Step " + stepCount);
        }

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

        stepCount++;
        sense();
    }

    /**
     * A handler of the voice button
     */
    private void voice() {
        boolean listening = voiceRecognizer.call(map);
        this.mainView.setVoiceListeningAction(listening);
    }

    /**
     * A handler of the auto button
     */
    private void auto() {
        if (autoStepTimer == null) {
            autoStepTimer = new Timer();
            autoStepTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    step();
                }
            }, 0, 1000);
        } else {
            autoStepTimer.cancel();
            autoStepTimer = null;
        }
        this.mainView.setAutoManualAction(autoStepTimer != null);
    }
}