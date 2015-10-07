package com.alive_n_clickin.commutity.domain;

import java.util.List;

/**
 * A class containing the same variables as a vehicle, in addition to the stop it's arriving to
 * and when it will get there. It also contains the flags for that vehicle.
 */
public class ArrivingVehicle extends Vehicle {
    private long timeToStop;
    private List<IFlag> flags;
    private long stopID;

    /**
     * Constructor.
     * @param destination
     * @param lineNumber
     * @param journeyID from Vasttrafik
     * @param timeToStop in milliseconds
     * @param flags can be null if no flags exist for this vehicle
     * @param stopID
     */
    public ArrivingVehicle(String destination, int lineNumber, long journeyID, long timeToStop,
                           List<IFlag> flags, long stopID) {
        super(destination, lineNumber, journeyID);
        this.timeToStop = timeToStop;
        this.flags = flags;
        this.stopID = stopID;
    }
}
