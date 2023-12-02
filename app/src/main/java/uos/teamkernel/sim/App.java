package uos.teamkernel.sim;

import teamkernel.sim.common.Spot;
import teamkernel.sim.model.Map;
import teamkernel.sim.model.MobileRobot;
import teamkernel.sim.common.Point;

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

        // Add 1 for boundary
        int mapWidth = initDialogForm.getMapSize().width + 1;
        int mapHeight = initDialogForm.getMapSize().height + 1;

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
        SimMainView simMainView = new SimMainView(realMap, robotMap, robot);

        // Initialize add-ons
        PathPlanner pathPlanner = new PathPlanner(robot.getPosition());
        VoiceRecognizer voiceRecognizer = new VoiceRecognizer();

        // Initialize controller
        new SimController(robot, robotMap, simMainView, pathPlanner, voiceRecognizer);
    }
}
