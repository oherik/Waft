package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.util.event.IObservable;

/**
 * An interface for classes that manages communication between the view and the underlying systems.
 * Implementations of this interface should always keep track of what bus the user is currently on.
 *
 * Classes that implement this interface should be observable, and emit CurrentBusChangeEvents when
 * the bus the user is currently on changes.
 */
public interface IBusManager extends IObservable {
    /**
     * Flags the bus the user is currently on. Will not do anything if the user isn't currently on
     * a bus.
     *
     * @param flag the flag to flag the current bus with.
     */
    void addFlagToCurrentBus(IFlag flag);

    /**
     * @return true if the user is currently on a bus, otherwise false.
     */
    boolean isOnBus();

    /**
     * @return the bus the user is currently on.
     */
    IBus getCurrentBus();
}