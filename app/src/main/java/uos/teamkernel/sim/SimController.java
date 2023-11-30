package uos.teamkernel.sim;

import java.util.Timer;
import java.util.TimerTask;

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

    static TimerTask task;

    int count = 0;

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
        this.mainView.addAutoManualButtonListener(e -> {
            JButton button = (JButton)e.getSource();
            String buttonText = button.getText();
            Timer scheduler = new Timer();
            if (buttonText.equals("Manual")) {
                button.setText("Auto");
                task = new TimerTask() {
                    @Override
                    public void run() {
                        step();
                    }
                };
                scheduler.scheduleAtFixedRate(task, 1000, 1000);
                task.run();
            } else if (buttonText.equals("Auto")) {
                button.setText("Manual");
                task.cancel();
            }
        });
    }

    /**
     * A handler of the step button
     */
    private void step() {
        Direction curr = mobileRobot.getDirection();

        // check if there is a hazard or color blob
        boolean hazardExistence = mobileRobot.senseHazard();
        if (hazardExistence) {
            Point hazard = mobileRobot.predictNextPosition(curr);
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
            if (map.getSpot(pos) == Spot.PREDEFINED_SPOT) {
                map.setSpot(pos, Spot.VISITED_PREDEFINED_SPOT);
            }
            System.out.println("[STEP] Moved to " + pos);
        }
    }

    /**
     * A handler of the voice button
     */
    private void voice(String buttonText) {
        voiceRecognizer.call(mobileRobot, map);
    }

}