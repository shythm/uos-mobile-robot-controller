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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.FlowLayout;
import java.awt.Font;

public class InitFormPrototype extends JFrame {
    private ArrayList<Integer> mapSize;
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

    public InitFormPrototype() {
        setTitle("InitForm");
        setMinimumSize(new Dimension(400, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 50, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("Input");
        title.setFont(new Font("Broadway", Font.PLAIN, 30));

        mapPanel = createPanel("Map Size");
        startPanel = createPanel("Start Point");
        destPanel = createPanel("Destination Point");
        colorPanel = createPanel("Color Blob");
        hazardPanel = createPanel("Hazard Spot");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        startButton = new JButton("Start");
        startButton.setSize(50, 200);
        buttonPanel.add(startButton);
        stopButton = new JButton("Stop");
        stopButton.setSize(50, 200);
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

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addStopButtonListener(ActionListener listener) {
        stopButton.addActionListener(listener);
    }

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

    public void parseMapData() {
        String mapSizeString = getTextFieldValue(mapPanel);
        mapSize = parseMapSize(mapSizeString);
        System.out.println(mapSize);

        String startPointString = getTextFieldValue(startPanel);
        for (Point point : parseCoordinates(startPointString)) {
            startPoint = point;
        }
        System.out.println(startPoint);

        String destPointString = getTextFieldValue(destPanel);
        destPoint = parseCoordinates(destPointString);
        System.out.println(destPoint);

        String colorPointString = getTextFieldValue(colorPanel);
        colorPoint = parseCoordinates(colorPointString);
        System.out.println(colorPoint);

        String hazardPointString = getTextFieldValue(hazardPanel);
        hazardPoint = parseCoordinates(hazardPointString);
        System.out.println(hazardPoint);
    }

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

    public static ArrayList<Integer> parseMapSize(String mapSizeString) {
        ArrayList<Integer> mapSizeParsed = new ArrayList<>();

        String pattern = "\\((\\d+),(\\d+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mapSizeString);

        if (m.find()) {
            int width = Integer.parseInt(m.group(1));
            int height = Integer.parseInt(m.group(2));
            mapSizeParsed.add(width);
            mapSizeParsed.add(height);
        }

        return mapSizeParsed;
    }

}
