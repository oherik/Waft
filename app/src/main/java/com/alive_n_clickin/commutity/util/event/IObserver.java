package com.alive_n_clickin.commutity.util.event;

/**
 * An interface for observers that can be registered to receive events from observables. When you
 * create an observer you specify what type of events it wants to receive. This makes it easy to
 * distinguish different event types when an observer is listening to several observables.
 *
 * @param <E> the type of event this observer wants to receive.
 */
public interface IObserver<E extends IEvent> {
    /**
     * This method gets called when an event gets sent from an observable that this observer
     * listens to. Each implementation decides what to do when this method gets called.
     *
     * @param event the event that occurred.
     */
    void onEvent(E event);
}
