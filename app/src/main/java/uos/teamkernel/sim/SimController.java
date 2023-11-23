package uos.teamkernel.sim;

import javax.swing.JButton;

import uos.teamkernel.common.Direction;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.view.SimMainView;

public class SimController {

    private MobileRobot mobileRobot;
    private Map map;
    private SimMainView mainView;

    private SimAddOn<Direction> pathPlanner;
    private SimAddOn<Void> voiceRecognizer;

    public SimController(MobileRobot mobileRobot, Map map, SimMainView mainView, SimAddOn<Direction> pathPlanner,
            SimAddOn<Void> voiceRecognizer) {
        this.mobileRobot = mobileRobot;
        this.map = map;

        this.pathPlanner = pathPlanner;
        this.voiceRecognizer = voiceRecognizer;

        this.mainView = mainView;
        this.mainView.addStepButtonListener(e -> step());
        this.mainView.addVoiceButtonListener(e -> {
            JButton button = (JButton)e.getSource();
            String buttonText = button.getText();

            if (buttonText.equals("Voice")) {
                button.setText("Stop");
            }

            else if (buttonText.equals("Stop")) {
                button.setText("Voice");
            }

            voice(buttonText);
        });
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
        boolean hazardExistence = mobileRobot.senseHazard();
        if (hazardExistence) {
            Point hazard = mobileRobot.predictNextPosition(next);
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
    }

    /**
     * A handler of the voice button
     */
    private void voice(String buttonText) {
        if (buttonText.equals("Voice")) {

        } else if (buttonText.equals(("Voice"))) {

        }
        voiceRecognizer.call(mobileRobot, map);
    }

}