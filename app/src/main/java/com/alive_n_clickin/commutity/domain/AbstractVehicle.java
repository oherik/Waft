package com.alive_n_clickin.commutity.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * An abstract class all other vehicles should extend. Holds a destination, a line number and
 * a journey number
 */
@EqualsAndHashCode
@ToString
public abstract class AbstractVehicle implements IVehicle {
    @Getter private final String destination;
    @Getter private final String shortRouteName;
    @Getter private final String journeyID;

    /**
     * Constructor
     * @param destination Where the vehicle is headed, e.g. "Sahlgrenska"
     * @param shortRouteName    The short version of the route name, usually simply a line number
     *                          (e.g. "55"), but can also be longer, e.g. "Grön Express"
     * @param journeyID from Vasttrafik. This is the unique identification number for a certain
     *                  route. It gets changed any time the vehicle arrives to the end stop and
     *                  continues in the opposite direction.
     * @throws NullPointerException if any parameter is null
     */
    public AbstractVehicle(@NonNull String destination, @NonNull String shortRouteName, String journeyID){
        this.destination = destination;
        this.shortRouteName = shortRouteName;
        this.journeyID = journeyID;
    }
}
