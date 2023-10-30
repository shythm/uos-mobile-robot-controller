package uos.teamkernel.model;

public interface IModelObserver {

    /**
     * Notify the observer that the model has changed
     * 
     * @param model the model itself
     */
    void modelChanged(Object model);

}
