package com.alive_n_clickin.commutity.event;

import com.alive_n_clickin.commutity.util.event.IEvent;

// TODO: Documentation
public class NewBusNearbyEvent implements IEvent {
    private String DGW;

    // TODO: Documentation
    public NewBusNearbyEvent(String DGW) {
        this.DGW = DGW;
    }

    // TODO: Documentation
    public String getDGW() {
        return this.DGW;
    }
}
