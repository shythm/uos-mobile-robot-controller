package uos.teamkernel.prototype;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class InitFormPrototype extends JFrame {
    public InitFormPrototype() {
        setTitle("InitForm");
        setMinimumSize(new Dimension(400, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 50, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("Input");
        title.setFont(new Font("Broadway", Font.PLAIN, 30));

        JPanel mapPanel = createPanel("Map Size");
        JPanel startPanel = createPanel("Start Point");
        JPanel destPanel = createPanel("Destination Point");
        JPanel colorPanel = createPanel("Color Blob");
        JPanel hazardPanel = createPanel("Hazard Spot");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton startButton = new JButton("Start");
        startButton.setSize(50, 200);
        buttonPanel.add(startButton);
        JButton stopButton = new JButton("Stop");
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
}
