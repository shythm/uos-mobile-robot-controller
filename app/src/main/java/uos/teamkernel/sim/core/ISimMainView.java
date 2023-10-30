package uos.teamkernel.sim.core;

import java.awt.event.ActionListener;
import uos.teamkernel.map.IMapModel;
import uos.teamkernel.robot.IMobileRobotModel;

public interface ISimMainView {

    /**
     * update the change of map model to this view
     * 
     * @param mapModel the map model to update
     */
    void updateMapModelChange(IMapModel mapModel);

    /**
     * update the change of mobile robot model to this view
     * 
     * @param mobileRobotModel the mobile robot model to update
     */
    void updateMobileRobotModelChange(IMobileRobotModel mobileRobotModel);

    /**
     * add step button listener
     * 
     * @param listener the handler of step button event
     */
    void addStepButtonListener(ActionListener listener);

    /**
     * add voice button listener
     * 
     * @param listener the handler of voice button event
     */
    void addVoiceButtonListener(ActionListener listener);

}