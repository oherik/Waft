package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.util.event.IEvent;

/**
 * Created by mats on 01/10/15.
 */
public class NewBusNearbyEvent implements IEvent {
    private String DGW;

    public NewBusNearbyEvent(String DGW) {
        this.DGW = DGW;
    }

    public String getDGW() {
        return this.DGW;
    }
}
