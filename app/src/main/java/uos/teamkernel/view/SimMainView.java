package uos.teamkernel.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import teamkernel.sim.model.MapModel;
import teamkernel.sim.model.MobileRobotModel;
import teamkernel.sim.model.ModelObserver;

public class SimMainView extends JFrame implements ModelObserver {

    private final JButton stepButton;
    private final JButton voiceButton;
    private final JButton autoManualButton;
    private final MapPanelView mapPanelView;

    public SimMainView(MapModel realMap, MapModel robotMap, MobileRobotModel robotModel) {
        super("MainView");

        // Map label
        JLabel mapLabel = new JLabel("Map");
        mapLabel.setFont(new Font("Broadway", Font.PLAIN, 30));
        mapLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        /**
         * Map panel graphical view (draw map and robot) this panel must be repaint when
         * model changed.
         */
        mapPanelView = new MapPanelView(realMap, robotMap, robotModel);
        mapPanelView.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapPanelView.setBackground(Color.WHITE);
        mapPanelView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Map panel
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
        mapPanel.add(mapLabel);
        mapPanel.add(mapPanelView);

        // Step, voice and auto-manual button
        stepButton = new JButton("Step");
        voiceButton = new JButton("Voice");
        autoManualButton = new JButton("Auto");

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(stepButton);
        buttonPanel.add(voiceButton);
        buttonPanel.add(autoManualButton);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(mapPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // register this view as observer of model
        realMap.addObserver(this);
        robotMap.addObserver(this);
        robotModel.addObserver(this);
    }

    public void modelChanged(Object model) {
        // repaint map panel view when model changed
        mapPanelView.repaint();
    }

    public void addStepButtonListener(ActionListener listener) {
        stepButton.addActionListener(listener);
    }

    public void addVoiceButtonListener(ActionListener listener) {
        voiceButton.addActionListener(listener);
    }

    public void setVoiceListeningAction(boolean isListening) {
        if (isListening) {
            voiceButton.setText("Stop");
        } else {
            voiceButton.setText("Voice");
        }
    }

    public void addAutoManualButtonListener(ActionListener listener) {
        autoManualButton.addActionListener(listener);
    }

    public void setAutoManualAction(boolean isAuto) {
        if (isAuto) {
            autoManualButton.setText("Manual");
        } else {
            autoManualButton.setText("Auto");
        }

        stepButton.setEnabled(!isAuto);
        voiceButton.setEnabled(!isAuto);
    }
}
