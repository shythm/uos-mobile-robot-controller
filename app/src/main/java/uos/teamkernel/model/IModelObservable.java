package uos.teamkernel.model;

public interface IModelObservable {

    /**
     * By calling this method, the observer will be notified when the model is
     * changed.
     * 
     * @param observer
     */
    void addObserver(IModelObserver observer);

}
