package uos.teamkernel.prototype;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.sim.core.SimMainView;

class SimMainViewPrototype extends JFrame implements SimMainView {
    // there is two button in this view for step and voice
    private JButton stepButton;
    private JButton voiceButton;

    private int mapWidth;
    private int mapHeight;
    private int mapPanelWidth;
    private int mapPanelHeight;
    private static Dimension mapSize;

    private static int padding = 50;
    private static int pointDist = 50;

    // class MapPanel extends JPanel {
    // Graphics2D g2;

    // public void paintComponent(Graphics g) {
    // super.paintComponent(g);
    // g2 = (Graphics2D)g;

    // Point rowStart = new Point(10, 10);
    // Point rowEnd = new Point(10, (int)(mapSize.getHeight() - 10));
    // Point colStart = new Point(10, 10);
    // Point colEnd = new Point((int)(mapSize.getWidth() - 10), 10);
    // for (int i = 0; i <= mapWidth; i++) {
    // g2.drawLine((int)rowStart.getX(), (int)rowStart.getY(), (int)rowEnd.getX(),
    // (int)rowEnd.getY());
    // rowStart.translate((int)((mapSize.getWidth() - 20) / mapWidth), 0);
    // rowEnd.translate((int)((mapSize.getWidth() - 20) / mapWidth), 0);
    // }
    // for (int i = 0; i <= mapHeight; i++) {
    // g2.drawLine((int)colStart.getX(), (int)colStart.getY(), (int)colEnd.getX(),
    // (int)colEnd.getY());
    // colStart.translate(0, (int)((mapSize.getHeight() - 20) / mapHeight));
    // colEnd.translate(0, (int)((mapSize.getHeight() - 20) / mapHeight));
    // }
    // }
    // }

    SimMainViewPrototype(MapPrototype mapModel, MobileRobotPrototype robotModel) {
        super("MainView");

        mapWidth = mapModel.getWidth();
        mapHeight = mapModel.getHeight();
        mapPanelWidth = mapWidth * pointDist;
        mapPanelHeight = mapHeight * pointDist;

        setMinimumSize(new Dimension(mapPanelWidth + 2 * padding, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
        JLabel mapLabel = new JLabel("Map");
        mapLabel.setFont(new Font("Broadway", Font.PLAIN, 30));
        mapLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        mapSize = new Dimension(mapPanelWidth + 2 * padding, mapPanelHeight + 2 * padding);
        // MapPanel map = new MapPanel();
        JPanel map = new JPanel() {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }

            private void drawBoard(Graphics g) {
                Point rowStart = new Point(padding, padding);
                Point rowEnd = new Point(padding, padding + mapPanelHeight);
                Point colStart = new Point(padding, padding);
                Point colEnd = new Point(padding + mapPanelWidth, padding);

                for (int i = 0; i <= mapWidth; i++) {
                    g.drawLine(rowStart.x, rowStart.y, rowEnd.x, rowEnd.y);
                    rowStart.translate(pointDist, 0);
                    rowEnd.translate(pointDist, 0);
                }

                for (int j = 0; j <= mapHeight; j++) {
                    g.drawLine(colStart.x, colStart.y, colEnd.x, colEnd.y);
                    colStart.translate(0, pointDist);
                    colEnd.translate(0, pointDist);
                }
            }
        };
        map.setPreferredSize(mapSize);
        map.setAlignmentX(Component.LEFT_ALIGNMENT);
        // DrawPanel map = new DrawPanel();
        map.setBackground(Color.WHITE);
        map.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mapPanel.add(mapLabel);
        mapPanel.add(map);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton stepButton = new JButton("Step");
        JButton voiceButton = new JButton("Voice");
        buttonPanel.add(stepButton);
        buttonPanel.add(voiceButton);

        mainPanel.add(mapPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void modelChanged(Object model) {
        /**
         * This method is called when the map or mobile robot has changed
         */
        if (model instanceof MapModel) {
        } else if (model instanceof MobileRobotModel) {
        }

        repaint();
    }

    public void addStepButtonListener(ActionListener listener) {
        stepButton.addActionListener(listener);
    }

    public void addVoiceButtonListener(ActionListener listener) {
        voiceButton.addActionListener(listener);
    }

}
