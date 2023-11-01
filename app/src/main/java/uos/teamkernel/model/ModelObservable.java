package uos.teamkernel.model;

public interface ModelObservable {

    /**
     * By calling this method, the observer will be notified when the model is
     * changed.
     * 
     * @param observer
     */
    void addObserver(ModelObserver observer);

}
