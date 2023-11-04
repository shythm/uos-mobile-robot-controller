package uos.teamkernel.prototype;

import javax.swing.*;
import java.awt.event.ActionListener;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.view.SimMainView;

class SimMainViewPrototype extends SimMainView {

    // there is two label in this view for map and mobile robot
    private JLabel mapLabel;
    private JLabel mobileRobotLabel;

    // there is two button in this view for step and voice
    private JButton stepButton;
    private JButton voiceButton;

    SimMainViewPrototype() {
        super("Sample Simulator");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        mapLabel = new JLabel("Map");
        mobileRobotLabel = new JLabel("Mobile Robot Position");

        stepButton = new JButton("Step");
        stepButton.addActionListener(e -> System.out.println("Step Button Clicked"));
        voiceButton = new JButton("Voice");
        voiceButton.addActionListener(e -> System.out.println("Voice Button Clicked"));

        // create vertical layout that contains two label and two button
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(mapLabel);
        add(mobileRobotLabel);
        add(stepButton);
        add(voiceButton);
    }

    private int countChangeMap = 0;
    private int countChangeRobot = 0;

    public void modelChanged(Object model) {
        /**
         * This method is called when the map or mobile robot has changed
         */
        if (model instanceof MapModel) {
            mapLabel.setText("Map has been changed, " + (++countChangeMap) + " times");
        } else if (model instanceof MobileRobotModel) {
            mobileRobotLabel.setText("Mobile Robot has been changed, " + (++countChangeRobot) + " times");
        }
    }

    public void addStepButtonListener(ActionListener listener) {
        stepButton.addActionListener(listener);
    }

    public void addVoiceButtonListener(ActionListener listener) {
        voiceButton.addActionListener(listener);
    }

}
