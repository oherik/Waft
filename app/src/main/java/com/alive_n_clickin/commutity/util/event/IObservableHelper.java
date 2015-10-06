package com.alive_n_clickin.commutity.util.event;

/**
 * An interface for classes that helps observables with their tasks. The thought is that an
 * observable should be able to delegate work to an ObservableHelper instead of managing all the
 * work of keeping track of observers itself.
 */
public interface IObservableHelper {
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers(IEvent event);
}
