package com.alive_n_clickin.commutity.util.event;

/**
 * An interface for classes that helps observables with their tasks. The thought is that an
 * observable should be able to delegate work to an ObservableHelper instead of managing all the
 * work of keeping track of observers itself.
 *
 * @param <E> the type of event this ObservableHelper will send to all registered observers.
 */
public interface IObservableHelper<E extends IEvent> {
    void addObserver(IObserver<E> observer);
    void removeObserver(IObserver<E> observer);
    void notifyObservers(E event);
}
