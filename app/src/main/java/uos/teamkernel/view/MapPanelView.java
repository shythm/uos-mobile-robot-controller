package uos.teamkernel.view;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.ImageIO;

import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

public class MapPanelView extends JPanel {

    private final int distance = 50;
    private final int padding = 50;
    private final int rows, cols;
    private final int width, height;

    private MapModel map;
    private MobileRobotModel mobileRobot;

    private Image imRobotN, imRobotE, imRobotS, imRobotW;
    private Image imDestination, imColorBlob, imHazard;
    private Image imDestinationGrey, imColorBlobGrey, imHazardGrey;

    public MapPanelView(MapModel map, MobileRobotModel mobileRobot) {
        super();

        this.map = map;
        this.mobileRobot = mobileRobot;

        // set the size of the board
        cols = map.getWidth();
        rows = map.getHeight();
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
                int centerX = ((i * distance) + padding);
                int centerY = (((rows - j - 1) * distance) + padding);

                Spot currentSpot = map.getSpot(i, j);

                if (currentSpot != null) {
                    Image imSpot = switch (map.getSpot(i, j)) {
                    case HAZARD -> imHazardGrey;
                    case COLOR_BLOB -> imColorBlobGrey;
                    case PREDEFINED_SPOT -> imDestination;
                    case PREDEFINED_SPOT_VISITED -> imDestinationGrey;
                    case COLOR_BLOB_SENSED -> imColorBlob;
                    case HAZARD_SENSED -> imHazard;
                    default -> null;
                    };

                    if (imSpot != null) {
                        g.drawImage(imSpot, centerX - (distance / 2), centerY - (distance / 2), this);
                    }
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
