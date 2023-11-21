package uos.teamkernel.view;

import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.ImageIO;

import uos.teamkernel.common.Point;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

public class MapPanelView extends JPanel {

    private final int distance = 50;
    private final int padding = 50;
    private final int rows, cols;
    private final int width, height;

    private MapModel map;
    private MobileRobotModel mobileRobot;

    private Image imRobotN, imRobotE, imRobotS, imRobotW, imDestination, imColorBlob, imHazard;

    public MapPanelView(MapModel map, MobileRobotModel mobileRobot) {
        super();

        this.map = map;
        this.mobileRobot = mobileRobot;

        // set the size of the board
        cols = map.getWidth() - 1;
        rows = map.getHeight() - 1;
        width = cols * distance;
        height = rows * distance;

        imRobotN = getResizedImage("/robotN.png");
        imRobotE = getResizedImage("/robotE.png");
        imRobotS = getResizedImage("/robotS.png");
        imRobotW = getResizedImage("/robotW.png");
        imDestination = getResizedImage("/destination.png");
        imColorBlob = getResizedImage("/colotBlob.png");
        imHazard = getResizedImage("/hazard.png");

        // set this component size
        setPreferredSize(new Dimension(cols * distance + padding * 2, rows * distance + padding * 2));
    }

    private Image getResizedImage(String path) {
        // TODO: Draw default image if image not found
        Image ret = null;

        try {
            BufferedImage bi = ImageIO.read(new File(getClass().getResource(path).getPath()));
            ret = bi.getScaledInstance(distance, distance, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Image " + path + " not found");
        }

        return ret;
    }

    private void drawBoard(Graphics g) {
        Point colStart = new Point(padding, padding);
        Point colEnd = new Point(padding + width, padding);
        Point rowStart = new Point(padding, padding);
        Point rowEnd = new Point(padding, padding + height);

        // draw horizontal lines
        for (int i = 0; i < rows + 1; i++) {
            g.drawLine(colStart.x, colStart.y, colEnd.x, colEnd.y);
            colStart.translate(0, distance);
            colEnd.translate(0, distance);
        }

        // draw vertical lines
        for (int i = 0; i < cols + 1; i++) {
            g.drawLine(rowStart.x, rowStart.y, rowEnd.x, rowEnd.y);
            rowStart.translate(distance, 0);
            rowEnd.translate(distance, 0);
        }
    }

    private void drawRobot(Graphics g) {
        // get the center of the robot
        int centerX = ((mobileRobot.getPosition().x * distance) + padding);
        int centerY = ((mobileRobot.getPosition().y * distance) + padding);

        Image imRobot = switch (mobileRobot.getDirection()) {
        case NORTH -> imRobotN;
        case EAST -> imRobotE;
        case SOUTH -> imRobotS;
        case WEST -> imRobotW;
        default -> null;
        };

        // draw the robot
        g.drawImage(imRobot, centerX - (distance / 2), centerY - (distance / 2), this);
    }

    private void drawSpots(Graphics g) {
        // TODO: Draw spots
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int centerX = ((i * distance) + padding);
                int centerY = ((j * distance) + padding);

                Image imSpot = switch (map.getSpot(i, j)) {
                case HAZARD -> imHazard;
                case COLOR_BLOB -> imColorBlob;
                case PREDEFINED_SPOT -> imDestination;
                default -> null;
                };

                if (imSpot != null) {
                    g.drawImage(imSpot, centerX - (distance / 2), centerY - (distance / 2), this);
                }
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawRobot(g);
        drawSpots(g);
    }
}
