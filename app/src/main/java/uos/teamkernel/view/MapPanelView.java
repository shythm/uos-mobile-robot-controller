package uos.teamkernel.view;

import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

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

    public MapPanelView(MapModel map, MobileRobotModel mobileRobot) {
        super();

        this.map = map;
        this.mobileRobot = mobileRobot;

        // set the size of the board
        cols = map.getWidth();
        rows = map.getHeight();
        width = cols * distance;
        height = rows * distance;

        // set this component size
        setPreferredSize(new Dimension(cols * distance + padding * 2, rows * distance + padding * 2));
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

}
