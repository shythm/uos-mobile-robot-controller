package uos.teamkernel.sim;

import uos.teamkernel.common.Spot;
import uos.teamkernel.common.Point;

import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;

import uos.teamkernel.view.InitDialogForm;
import uos.teamkernel.view.SimMainView;

public class App {
    public static void main(String[] args) {
        // Ask for input to initialize
        InitDialogForm initDialogForm = new InitDialogForm(Thread.currentThread());
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("Initialize done. Start Simulator.");
        }

        int mapWidth = (int)initDialogForm.getMapSize().getWidth();
        int mapHeight = (int)initDialogForm.getMapSize().getHeight();

        // Initialize map
        MapModel realMap = new Map(mapWidth, mapHeight);
        for (Point p : initDialogForm.getHazardPoint()) { // set hazard spot
            realMap.setSpot(p, Spot.HAZARD);
        }
        for (Point p : initDialogForm.getColorPoint()) { // set color blob spot
            realMap.setSpot(p, Spot.COLOR_BLOB);
        }
        for (Point p : initDialogForm.getDestPoint()) { // set destination spot
            realMap.setSpot(p, Spot.PREDEFINED_SPOT);
        }

        // Initialize robot
        MapModel robotMap = new Map(mapWidth, mapHeight);
        MobileRobotModel robot = new MobileRobot();

        new SimMainView(robotMap, robot);
    }
}
