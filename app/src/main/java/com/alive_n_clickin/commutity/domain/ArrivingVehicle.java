package com.alive_n_clickin.commutity.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

/**
 * A class containing the same variables as a vehicle, in addition to a variable showing when it
 * will arrive to a specified stop. It also contains the flags for that vehicle.
 */
public class ArrivingVehicle extends Vehicle implements IArrivingVehicle {
    @Getter private long timeToArrival;
    @Getter private List<IFlag> flags;

    /**
     * Constructor.
     * @param destination Where the vehicle is headed, e.g. "Sahlgrenska"
     * @param shortRouteName    The short version of the route name, usually simply a line number
     *                          (e.g. "55"), but can also be longer, e.g. "Grön Express"
     * @param journeyID from Vasttrafik. This is the unique identification number for a certain
     *                  route. It gets changed any time the vehicle arrives to the end stop and
     *                  continues in the opposite direction.
     * @param timeToArrival in milliseconds. This should be the real time to arrival, not the
     *                      scheduled
     * @param flags can be null if no flags exist for this vehicle
     * @throws NullPointerException if any parameter is null
     */
    public ArrivingVehicle(@NonNull String destination, @NonNull String shortRouteName,
                           long journeyID, long timeToArrival, @NonNull List<IFlag> flags){
        super(destination, shortRouteName, journeyID);
        this.timeToArrival = timeToArrival;
        this.flags = new ArrayList<>(flags);
    }
}
