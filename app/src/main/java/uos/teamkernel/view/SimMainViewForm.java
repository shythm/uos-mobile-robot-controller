package uos.teamkernel.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uos.teamkernel.common.Direction;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.sim.core.SimMainView;

class SimMainViewForm extends JFrame implements SimMainView {
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

    SimMainViewForm(MapModel mapModel, MobileRobotModel robotModel) {
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
            private Direction robotDirection;
            private Dimension mapSize;
            private Point robotPosition;
            private Spot[][] mapSpot;

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                drawBoard(g2);
                drawRobot(g2);
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

            private void drawRobot(Graphics2D g) {
                Image robot = new ImageIcon("robot.png").getImage();
                int center_x = ((robotPosition.x * pointDist) + padding);
                int center_y = ((robotPosition.y * pointDist) + padding);
                AffineTransform transform = new AffineTransform();
                double rotateAngle = Math.toRadians(90 * robotDirection.ordinal());
                transform.rotate(rotateAngle, center_x, center_y);
                g.setTransform(transform);

                g.drawImage(robot, center_x - (pointDist / 2), center_y - (pointDist / 2), null);

            }

            private void drawSpot(Graphics2D g) {
                for (int i = 0; i < mapSize.width; i++) {
                    for (int j = 0; j < mapSize.height; j++) {
                        if (mapSpot[i][j] != Spot.NONE) {
                            String spotName = mapSpot[i][j].toString().toLowerCase();
                            String fileName = spotName + ".png";
                            Image spotIcon = new ImageIcon(fileName).getImage();
                            g.drawImage(spotIcon, i, j, null);
                        }
                    }
                }

            }

            public void setRobot(MobileRobotModel robot) {
                robotDirection = robot.getDirection();
                robotPosition = robot.getPosition();
            }

            public void setMapSize(Dimension mapSize) {
                this.mapSize = mapSize;
            }

            public void setMapStop(MapModel map) {
                for (int i = 0; i < mapSize.getWidth(); i++) {
                    for (int j = 0; j < mapSize.getHeight(); j++) {
                        mapSpot[i][j] = map.getSpot(new Point(i, j));
                    }
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
        // setVisible(true);
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
