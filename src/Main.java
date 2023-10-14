import core.SimController;

class Main {

    public static void main(String[] args) {
        IMapModel map;
        IMobileRobotModel mobileRobot;
        
        SimMainView mainView = new SimMainView(mobileRobot, map);
        SimController controller = new SimController(mobileRobot, map, mainView);

        mainView.setVisible(true);
    }

}