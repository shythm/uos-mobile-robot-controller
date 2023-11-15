package uos.teamkernel.prototype;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import uos.teamkernel.sim.core.SimController;

public class AppPrototype {

    public static void main(String[] args) {

        InitFormPrototype initForm = new InitFormPrototype();
        initForm.addParentsThread(Thread.currentThread());

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Simulater Start");
        }

        // create model (map, mobile robot)
        MapPrototype map = new MapPrototype();
        MobileRobotPrototype mobileRobot = new MobileRobotPrototype();

        // create view
        SimMainViewPrototype mainView = new SimMainViewPrototype(map, mobileRobot);
        // map.addObserver(mainView);
        // mobileRobot.addObserver(mainView);

        // // create add-ons
        // PathPlannerPrototype pathPlanner = new PathPlannerPrototype();
        // VoiceRecognizerPrototype voiceRecognizer = new VoiceRecognizerPrototype();

        // // create simulator
        // new SimController(mobileRobot, map, mainView, pathPlanner, voiceRecognizer);
        // mainView.setVisible(true);

    }
}
