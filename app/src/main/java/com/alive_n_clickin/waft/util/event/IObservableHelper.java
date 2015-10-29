package com.alive_n_clickin.waft.util.event;

/**
 * An interface for classes that helps observables with their tasks. The thought is that an
 * observable should be able to delegate work to an ObservableHelper instead of managing all the
 * work of keeping track of observers itself.
 *
 * @since 0.2
 */
public interface IObservableHelper {
    /**
     * Adds an observable to this ObservableHelper. The onEvent method in this observer will be
     * called when notifyObservers is run.
     *
     * @param observer the observer to add.
     */
    void addObserver(IObserver observer);

    /**
     * Removes an observable from this ObservableHelper. The onEvent method in this observer will no
     * longer be called when notifyObservers is run.
     *
     * @param observer The observer to remove.
     */
    void removeObserver(IObserver observer);

    /**
     * Notifies all added observers about an event. The onEvent method will be called on all observers.
     *
     * @param event the event to notify observers about.
     */
    void notifyObservers(IEvent event);
}
