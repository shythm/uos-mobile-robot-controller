package uos.teamkernel.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.model.ModelObserver;

public class SimMainView extends JFrame implements ModelObserver {

    private final JButton stepButton;
    private final JButton voiceButton;
    private final MapPanelView mapPanelView;

    public SimMainView(MapModel mapModel, MobileRobotModel robotModel) {
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
        mapPanelView = new MapPanelView(mapModel, robotModel);
        mapPanelView.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapPanelView.setBackground(Color.WHITE);
        mapPanelView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Map panel
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
        mapPanel.add(mapLabel);
        mapPanel.add(mapPanelView);

        // Step and voice button
        stepButton = new JButton("Step");
        voiceButton = new JButton("Voice");

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(stepButton);
        buttonPanel.add(voiceButton);

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
        mapModel.addObserver(this);
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
}
