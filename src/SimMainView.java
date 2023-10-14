// Using swing
// there is grid layout in the main frame (left and right)
// two buttons and four textfields below are in the left right layout
// two buttons that are 'STEP' and 'VOICE'
// four textfields that are 'Map Size', 'Start Point', 'Destination Spots', 'Hazard Spots'
// there is a picturebox in the left layout

// Path: src/views/SimMainView.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import core.*;

class SimMainView : JFrame {

    // initialize the components
    private JButton initButton = new JButton("INIT");
    private JButton stepButton = new JButton("STEP");
    private JButton voiceButton = new JButton("VOICE");
    private JTextField mapSizeField = new JTextField("10");
    private JTextField startPointField = new JTextField("0,0");
    private JTextField destinationSpotsField = new JTextField("9,9");
    private JTextField hazardSpotsField = new JTextField("1,1;2,2;3,3;4,4;5,5;6,6;7,7;8,8");
    private JLabel pictureLabel = new JLabel();

    // constructor
    public SimMainView() {
        // set the title of the frame
        super("Mobile Robot Simulator");

        // set the size of the frame
        setSize(800, 600);

        // set the layout of the frame
        setLayout(new GridLayout(1, 2));

        // set the layout of the left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // set the layout of the right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1));

        // add the left and right panel to the frame
        add(leftPanel);
        add(rightPanel);

        // add the picture label to the left panel
        leftPanel.add(pictureLabel, BorderLayout.CENTER);

        // add the map size field to the right panel
        rightPanel.add(mapSizeField);

        // add the start point field to the right panel
        rightPanel.add(startPointField);

        // add the destination spots field to the right panel
        rightPanel.add(destinationSpotsField);

        // add the hazard spots field to the right panel
        rightPanel.add(hazardSpotsField);

        // add the init button to the right panel
        rightPanel.add(initButton);

        // add the step button to the right panel
        rightPanel.add(stepButton);

        // add the voice button to the right panel
        rightPanel.add(voiceButton);

        // set the frame to be visible
        setVisible(true);
    }

    public void addInitButtonEvent(ActionListener listener) {
        initButton.addActionListener(listener);
    };
}