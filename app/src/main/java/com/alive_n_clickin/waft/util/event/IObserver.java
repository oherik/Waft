package com.alive_n_clickin.waft.util.event;

/**
 * An interface for observers that can be registered to receive events from observables.
 *
 * @since 0.2
 */
public interface IObserver {
    /**
     * This method gets called when an event gets sent from an observable that this observer
     * listens to. Each implementation decides what to do when this method gets called.
     *
     * @param event the event that occurred.
     */
    void onEvent(IEvent event);
}
