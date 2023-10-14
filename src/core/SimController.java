import java.awt.Point;
import core.IMobileRobotModel;

// Mobile Robot Simulator Controller
class SimController {

    private IMobileRobotModel mobileRobot;
    private IMapModel map;
    private ISimMainView mainView;

    private ISimAddOn[] addOns;

    // constructor
    public SimController(IMobileRobotModel mobileRobot, IMapModel map, ISimMainView mainView) {
        this.mobileRobot = mobileRobot;
        this.map = map;
        this.mainView = mainView;
    }

    /**
     * calculate the next direction of the robot to move to the target position
     * the robot only can move one unit in the direction it is facing
     * so to move to the target position, it needs to turn several times
     * 
     * if there is no need to turn, it will return false
     * if it needs to turn 90 degrees clockwise, it will return true
     */
    private boolean isNeedToTurn(Point targPos) {
        int currDir = mobileRobot.getDirection();

        // TODO: implement this function
        return false;
    }

    public void step() {
        Point targetPos;

        for (ISimAddOn addOn : addOns) {
            targetPos = addOn.step(this, targetPos);
        }

        if (isNeedToTurn(targetPos)) {
            mobileRobot.turn();
        } else {
            mobileRobot.move();
        }

        mainView.update();
    }

    public void registAddOn(ISimAddOn addOn) {
        addOn.init(this);
        addOns.add(addOn);
    }

    public void 
}