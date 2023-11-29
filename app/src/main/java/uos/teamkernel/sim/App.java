package uos.teamkernel.sim;

import uos.teamkernel.common.Spot;
import uos.teamkernel.common.Point;
import uos.teamkernel.common.Direction;

import uos.teamkernel.view.InitDialogForm;
import uos.teamkernel.view.SimMainView;

import uos.teamkernel.prototype.PathPlannerPrototype;

public class App {
    public static void main(String[] args) {
        // Ask for input to initialize
        InitDialogForm initDialogForm = new InitDialogForm(Thread.currentThread());
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("Initialize done. Start Simulator.");
        }

        // Add 1 for boundary
        int mapWidth = (int)initDialogForm.getMapSize().getWidth() + 1;
        int mapHeight = (int)initDialogForm.getMapSize().getHeight() + 1;

        // Initialize map
        Map realMap = new Map(mapWidth, mapHeight);
        for (Point p : initDialogForm.getHazardPoint()) { // set hazard spot
            realMap.setSpot(p, Spot.HAZARD);
        }
        for (Point p : initDialogForm.getColorPoint()) { // set color blob spot
            realMap.setSpot(p, Spot.COLOR_BLOB);
        }

        // Initialize robot
        MobileRobot robot = new MobileRobot(realMap, initDialogForm.getStartPoint());

        // Initialize robot map for storing robot's sensing result
        Map robotMap = new Map(mapWidth, mapHeight);
        for (Point p : initDialogForm.getDestPoint()) { // set destination spot
            robotMap.setSpot(p, Spot.PREDEFINED_SPOT);
        }

        // Initialize view
        SimMainView simMainView = new SimMainView(robotMap, robot);

        // Initialize add-ons
        SimAddOn<Direction> pathPlanner = new PathPlannerPrototype();
        SimAddOn<Void> voiceRecognizer = new VoiceRecognizer();

        // Initialize controller
        new SimController(robot, robotMap, simMainView, pathPlanner, voiceRecognizer);
    }
}
