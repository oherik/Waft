package com.alive_n_clickin.commutity.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * A class containing the same variables as a vehicle, in addition to the stop it's arriving to
 * and when it will get there. It also contains the flags for that vehicle.
 */
public class ArrivingVehicle extends Vehicle {
    @Getter private long timeToArrival;
    @Getter private List<IFlag> flags;

    /**
     * Constructor.
     * @param destination
     * @param lineNumber
     * @param journeyID from Vasttrafik
     * @param timeToArrival in milliseconds
     * @param flags can be null if no flags exist for this vehicle
     */
    public ArrivingVehicle(String destination, String lineNumber, long journeyID, long timeToArrival,
                           List<IFlag> flags) {
        super(destination, lineNumber, journeyID);
        this.timeToArrival = timeToArrival;
        this.flags = new ArrayList<>(flags);
    }
}
