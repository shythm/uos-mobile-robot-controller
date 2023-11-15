package uos.teamkernel.prototype;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.FlowLayout;
import java.awt.Font;

public class InitFormPrototype extends JFrame {
    private Dimension mapSize;
    private Point startPoint;
    private ArrayList<Point> destPoint;
    private ArrayList<Point> colorPoint;
    private ArrayList<Point> hazardPoint;

    private JButton startButton;
    private JButton stopButton;

    private JPanel mapPanel;
    private JPanel startPanel;
    private JPanel destPanel;
    private JPanel colorPanel;
    private JPanel hazardPanel;

    private Thread parentThread;

    public InitFormPrototype() {
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
        mapPanel = createPanel("Map Size");
        startPanel = createPanel("Start Point");
        destPanel = createPanel("Destination Point");
        colorPanel = createPanel("Color Blob");
        hazardPanel = createPanel("Hazard Spot");

        /* Button Panel with Step button and Voice button */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        startButton = new JButton("Start");
        startButton.setSize(50, 200);
        startButton.addActionListener((e) -> {
            saveMapData();
            if (mapSize != null & startPoint != null & destPoint.size() != 0 & colorPoint.size() != 0
                    & hazardPoint.size() != 0) {
                this.parentThread.interrupt();
                this.dispose();
            }
        });
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

    }

    // Method for make JPanel with a JLabel and a JTextField
    private static JPanel createPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Britannic", Font.PLAIN, 20));
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    // Get Text in JTextField in JPanel
    private static String getTextFieldValue(JPanel panel) {
        Component components[] = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField)component;
                return textField.getText();
            }
        }
        return null;
    }

    // Save Parsed Map Data
    public void saveMapData() {
        String mapSizeString = getTextFieldValue(mapPanel);
        mapSize = parseMapSize(mapSizeString);

        String startPointString = getTextFieldValue(startPanel);
        for (Point point : parseCoordinates(startPointString)) {
            startPoint = point;
        }

        String destPointString = getTextFieldValue(destPanel);
        destPoint = parseCoordinates(destPointString);

        String colorPointString = getTextFieldValue(colorPanel);
        colorPoint = parseCoordinates(colorPointString);

        String hazardPointString = getTextFieldValue(hazardPanel);
        hazardPoint = parseCoordinates(hazardPointString);
    }

    // Parse Coordinates of Multi Points
    public static ArrayList<Point> parseCoordinates(String text) {
        ArrayList<Point> points = new ArrayList<>();

        String pattern = "\\((\\d+),(\\d+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);

        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            points.add(new Point(x, y));
        }

        return points;
    }

    // Parse Map Size value
    public static Dimension parseMapSize(String mapSizeString) {
        Dimension mapSizeParsed;

        String pattern = "\\((\\d+),(\\d+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mapSizeString);

        if (m.find()) {
            int width = Integer.parseInt(m.group(1));
            int height = Integer.parseInt(m.group(2));
            mapSizeParsed = new Dimension(width, height);
            return mapSizeParsed;
        }
        return null;
    }

    public void addParentsThread(Thread parentThread) {
        this.parentThread = parentThread;
    }

    public Dimension getMapsize() {
        return mapSize;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public ArrayList<Point> getDestPoint() {
        return destPoint;
    }

    public ArrayList<Point> getColorPoint() {
        return colorPoint;
    }

    public ArrayList<Point> getHazardPoint() {
        return hazardPoint;
    }
}
