package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.util.event.IEvent;

/**
 * Created by mats on 02/10/15.
 */
public class CurrentBusChangeEvent implements IEvent {
    private IBus bus;

    public CurrentBusChangeEvent(IBus bus) {
        this.bus = bus;
    }

    public IBus getBus() {
        return this.bus;
    }
}
