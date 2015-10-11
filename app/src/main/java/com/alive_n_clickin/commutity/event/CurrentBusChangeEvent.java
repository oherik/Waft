package com.alive_n_clickin.commutity.event;

import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.util.event.IEvent;

/**
 * An event for when the bus the user is on changes. The event contains information about which
 * bus the user is currently on.
 */
public class CurrentBusChangeEvent implements IEvent {
    private IElectriCityBus bus;

    /**
     * Initiates a new CurrentBusChangeEvent.
     *
     * @param bus the bus that the user is currently on. Can be null.
     */
    public CurrentBusChangeEvent(IElectriCityBus bus) {
        this.bus = bus;
    }

    /**
     * @return the bus the user is currently on. Null if the user isn't on a bus.
     */
    public IElectriCityBus getBus() {
        return this.bus;
    }
}
