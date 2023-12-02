package uos.teamkernel.view;

import teamkernel.sim.common.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitDialogForm extends JFrame {
    private JButton startButton;
    private JButton stopButton;

    private InputArea mapPanel;
    private InputArea startPanel;
    private InputArea destPanel;
    private InputArea colorPanel;
    private InputArea hazardPanel;

    private Thread parentThread;

    public InitDialogForm(Thread parentThread) {
        // save parent thread for interrupt
        this.parentThread = parentThread;

        setTitle("InitForm");
        setMinimumSize(new Dimension(400, 0));

        /*
         * Main Panel For manage all other label and panels
         */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 50, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        /*
         * Title Label For title text
         */
        JLabel title = new JLabel("Input");
        title.setFont(new Font("Broadway", Font.PLAIN, 30));

        /*
         * Panels with label For text of type of map data and textfield for input map
         * data
         */
        mapPanel = new InputArea("Map Size");
        startPanel = new InputArea("Start Point");
        destPanel = new InputArea("Destination Point");
        colorPanel = new InputArea("Color Blob");
        hazardPanel = new InputArea("Hazard Spot");

        /* Button Panel with Start Button and Stop Button */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        startButton = new JButton("Start");
        startButton.setSize(50, 200);
        startButton.addActionListener(new StartButtonActionListener());
        buttonPanel.add(startButton);
        stopButton = new JButton("Stop");
        stopButton.setSize(50, 200);
        stopButton.addActionListener((e) -> {
            System.exit(0);
        });
        buttonPanel.add(stopButton);

        mainPanel.add(title);
        mainPanel.add(mapPanel);
        mainPanel.add(startPanel);
        mainPanel.add(destPanel);
        mainPanel.add(colorPanel);
        mainPanel.add(hazardPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Test code for input
        if (true) {
            mapPanel.textField.setText("(4 5)");
            startPanel.textField.setText("(1 2)");
            destPanel.textField.setText("((4 2)(1 5))");
            colorPanel.textField.setText("((2 2)(4 4))");
            hazardPanel.textField.setText("((1 0)(3 2)(2 3)(2 4)(2 5))");
        }
    }

    // Event Handler for Start Button
    class StartButtonActionListener implements ActionListener {
        private void showErrorMessage(String message) {
            JOptionPane.showMessageDialog((Component)startButton, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        public void actionPerformed(ActionEvent e) {
            // check if all input is valid
            if (getMapSize() == null) {
                showErrorMessage("Map Size is Invalid");
                return;
            }
            if (getStartPoint() == null) {
                showErrorMessage("Start Point is Invalid");
                return;
            }
            if (getDestPoint().size() == 0) {
                showErrorMessage("Destination Point is Invalid");
                return;
            }

            parentThread.interrupt();
            dispose();
        }
    }

    class InputArea extends JPanel {
        private JLabel label;
        private JTextField textField;

        public InputArea(String label) {
            setLayout(new GridLayout(0, 1, 5, 10));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));

            this.label = new JLabel(label);
            this.label.setFont(new Font("Britannic", Font.PLAIN, 20));

            this.textField = new JTextField();

            add(this.label);
            add(this.textField);
        }

        public String getText() {
            return textField.getText();
        }
    }

    // Parse Coordinates of Multi Points
    public static ArrayList<Point> parseCoordinates(String text) {
        ArrayList<Point> points = new ArrayList<>();

        String pattern = "\\((\\d+)\\s(\\d+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);

        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            points.add(new Point(x, y));
        }

        return points;
    }

    public Dimension getMapSize() {
        String mapSizeString = mapPanel.getText();
        ArrayList<Point> mapSize = parseCoordinates(mapSizeString);

        if (mapSize.size() == 0) {
            return null;
        } else {
            Point point = mapSize.get(0);
            return new Dimension(point.x, point.y);
        }
    }

    public Point getStartPoint() {
        String startPointString = startPanel.getText();
        ArrayList<Point> startPoint = parseCoordinates(startPointString);

        if (startPoint.size() == 0) {
            return null;
        } else {
            return startPoint.get(0);
        }
    }

    public ArrayList<Point> getDestPoint() {
        String destPointString = destPanel.getText();
        return parseCoordinates(destPointString);
    }

    public ArrayList<Point> getColorPoint() {
        String colorPointString = colorPanel.getText();
        return parseCoordinates(colorPointString);
    }

    public ArrayList<Point> getHazardPoint() {
        String hazardPointString = hazardPanel.getText();
        return parseCoordinates(hazardPointString);
    }
}
