package uos.teamkernel.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import uos.teamkernel.model.ModelObserver;

public abstract class SimMainView extends JFrame implements ModelObserver {

    /**
     * Constructor
     * 
     * @param title the title of the view
     */
    public SimMainView(String title) {
        super(title);
    }

    /**
     * add step button listener
     * 
     * @param listener the handler of step button event
     */
    public abstract void addStepButtonListener(ActionListener listener);

    /**
     * add voice button listener
     * 
     * @param listener the handler of voice button event
     */
    public abstract void addVoiceButtonListener(ActionListener listener);

}