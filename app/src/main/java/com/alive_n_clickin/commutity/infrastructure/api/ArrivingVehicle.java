package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.Bus;

import java.util.Date;

import lombok.Getter;
import lombok.NonNull;

/** Consists of a vehicle and when it will arrive to a certain stop
 */
public class ArrivingVehicle {
    @Getter private Bus bus;
    @Getter private Date arrival;

    /**
     * Constructor
     * @param bus
     * @param arrival
     */
    public ArrivingVehicle(@NonNull Bus bus, @NonNull Date arrival){
        this.bus = bus;
        this.arrival = arrival;
    }

}
