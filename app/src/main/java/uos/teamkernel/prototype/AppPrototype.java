package uos.teamkernel.prototype;

import uos.teamkernel.sim.core.SimController;

public class AppPrototype {

    public static void main(String[] args) {

        // create model (map, mobile robot)
        MapPrototype map = new MapPrototype();
        MobileRobotPrototype mobileRobot = new MobileRobotPrototype();

        // create view
        SimMainViewPrototype mainView = new SimMainViewPrototype();
        map.addObserver(mainView);
        mobileRobot.addObserver(mainView);

        // create add-ons
        PathPlannerPrototype pathPlanner = new PathPlannerPrototype();
        VoiceRecognizerPrototype voiceRecognizer = new VoiceRecognizerPrototype();

        // create simulator
        new SimController(mobileRobot, map, mainView, pathPlanner, voiceRecognizer);
        mainView.setVisible(true);
    }
}
