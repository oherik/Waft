package com.alive_n_clickin.commutity.util.event;

/**
 * An interface for observables.
 *
 */
public interface IObservable {
    /**
     * Adds an observer to the observable. The onEvent method in the observer will be called when
     * the observable sends events.
     *
     * @param observer an observer that should receive events from the observable.
     */
    void addObserver(IObserver observer);

    /**
     * Removes an observer from the observable. The onEvent method in the observer will no longer be
     * called when the observable sends events.
     *
     * @param observer an observer that no longer wishes to receive events from the observable.
     */
    void removeObserver(IObserver observer);
}
