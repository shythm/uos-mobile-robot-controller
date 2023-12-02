package teamkernel.sim.model;

public interface ModelObserver {

    /**
     * Notify the observer that the model has changed
     * 
     * @param model the model itself
     */
    void modelChanged(Object model);

}
