package com.alive_n_clickin.waft.application;

import com.alive_n_clickin.waft.util.event.IEvent;

/**
 * An event for when a new bus is nearby. The event contains the DGW of the bus.
 *
 * @since 0.2
 */
public class NewBusNearbyEvent implements IEvent {
    private String DGW;

    /**
     * Initates a new NewBusNearbyEvent.
     *
     * @param DGW the DGW of the nearby bus.
     */
    public NewBusNearbyEvent(String DGW) {
        this.DGW = DGW;
    }

    /**
     * @return the DGW of the nearby bus.
     */
    public String getDGW() {
        return this.DGW;
    }
}
