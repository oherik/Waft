package com.alive_n_clickin.commutity.domain;

import lombok.Getter;
import lombok.NonNull;

/**
 * An abstract class all other vehicles should extend. Holds a destination, a line number and
 * a journey number
 */
public abstract class Vehicle {
    @Getter private String destination;
    @Getter private String shortName;
    @Getter private long journeyID;

    /**
     * Constructor
     * @param destination
     * @param shortName
     * @param journeyID from Vasttrafik
     * @throws NullPointerException if any parameter is null
     */
    public Vehicle(@NonNull String destination, @NonNull String shortName, long journeyID){
        this.destination = destination;
        this.shortName = shortName;
        this.journeyID = journeyID;
    }
}
