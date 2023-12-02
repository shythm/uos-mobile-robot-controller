package uos.teamkernel.view;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.ImageIO;

import teamkernel.sim.common.Point;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

public class MapPanelView extends JPanel {

    private final int distance = 50;
    private final int padding = 50;
    private final int rows, cols;
    private final int width, height;

    private MapModel realMap;
    private MapModel robotMap;
    private MobileRobotModel mobileRobot;

    private Image imRobotN, imRobotE, imRobotS, imRobotW;
    private Image imDestination, imColorBlob, imHazard;
    private Image imDestinationGrey, imColorBlobGrey, imHazardGrey;

    public MapPanelView(MapModel realMap, MapModel robotMap, MobileRobotModel mobileRobot) {
        super();

        this.realMap = realMap;
        this.robotMap = robotMap;
        this.mobileRobot = mobileRobot;

        // set the size of the board
        cols = realMap.getWidth();
        rows = realMap.getHeight();
        width = (cols - 1) * distance;
        height = (rows - 1) * distance;

        imRobotN = getResizedImage("/robotN.png");
        imRobotE = getResizedImage("/robotE.png");
        imRobotS = getResizedImage("/robotS.png");
        imRobotW = getResizedImage("/robotW.png");
        imDestination = getResizedImage("/destination.png");
        imDestinationGrey = getResizedImage("/destinationGrey.png");
        imColorBlob = getResizedImage("/colorBlob.png");
        imColorBlobGrey = getResizedImage("/colorBlobGrey.png");
        imHazard = getResizedImage("/hazard.png");
        imHazardGrey = getResizedImage("/hazardGrey.png");

        // set this component size
        setPreferredSize(new Dimension(width + padding * 2, height + padding * 2));
    }

    private Image getResizedImage(String path) {
        Image ret = null;

        try {
            String fixedPath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");
            BufferedImage bi = ImageIO.read(new File(fixedPath));
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
        for (int i = 0; i < rows; i++) {
            g.drawLine(colStart.x, colStart.y, colEnd.x, colEnd.y);
            colStart.translate(0, distance);
            colEnd.translate(0, distance);
        }

        // draw vertical lines
        for (int i = 0; i < cols; i++) {
            g.drawLine(rowStart.x, rowStart.y, rowEnd.x, rowEnd.y);
            rowStart.translate(distance, 0);
            rowEnd.translate(distance, 0);
        }
    }

    private void drawRobot(Graphics g) {
        // get the center of the robot
        int centerX = ((mobileRobot.getPosition().x * distance) + padding);
        int centerY = (((rows - mobileRobot.getPosition().y - 1) * distance) + padding);

        Image imRobot = switch (mobileRobot.getDirection()) {
        case NORTH -> imRobotN;
        case EAST -> imRobotE;
        case SOUTH -> imRobotS;
        case WEST -> imRobotW;
        default -> null;
        };

        // draw the robot
        if (imRobot != null) {
            g.drawImage(imRobot, centerX - (distance / 2), centerY - (distance / 2), this);
        }
    }

    private void drawSpots(Graphics g) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Image imSpot = null;
                int centerX = ((i * distance) + padding);
                int centerY = (((rows - j - 1) * distance) + padding);

                // draw all given hazard and color blob spots
                imSpot = switch (realMap.getSpot(i, j)) {
                case HAZARD -> imHazardGrey;
                case COLOR_BLOB -> imColorBlobGrey;
                default -> null;
                };

                // draw all sensed spots
                imSpot = switch (robotMap.getSpot(i, j)) {
                case HAZARD -> imHazard;
                case COLOR_BLOB -> imColorBlob;
                case PREDEFINED_SPOT -> imDestination;
                case PREDEFINED_SPOT_VISITED -> imDestinationGrey;
                default -> imSpot;
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
        drawSpots(g);
        drawRobot(g);
    }
}
