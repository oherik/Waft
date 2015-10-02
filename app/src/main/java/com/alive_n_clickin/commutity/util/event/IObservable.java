package com.alive_n_clickin.commutity.util.event;

/**
 * An interface for observables. When you create an observable you specify what type of events it
 * will produce. This makes it easy to distinguish different event types when an observable is
 * responsible for sending events for multiple different occasions.
 *
 * @param <E> the type of events this observable sends.
 */
public interface IObservable<E extends IEvent> {
    /**
     * Adds an observer to the observable. The onEvent method in the observer will be called when
     * the observable sends events.
     *
     * @param observer an observer that should receive events from the observable.
     */
    void addObserver(IObserver<E> observer);

    /**
     * Removes an observer from the observable. The onEvent method in the observer will no longer be
     * called when the observable sends events.
     *
     * @param observer an observer that no longer wishes to receive events from the observable.
     */
    void removeObserver(IObserver<E> observer);
}
