package uos.teamkernel.sim.core;

import java.awt.event.ActionListener;
import uos.teamkernel.model.IModelObserver;

public interface ISimMainView extends IModelObserver {

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