package com.alive_n_clickin.commutity.domain;

import lombok.Getter;

/**
 * An abstract class all other vehicles should extend. Holds a destination, a line number and
 * a journey number
 */
public abstract class Vehicle {
    @Getter private String destination;
    @Getter private int lineNumber;
    @Getter private long journeyID;

    /**
     * Constructor
     * @param destination
     * @param lineNumber
     * @param journeyID from Vasttrafik
     */
    public Vehicle(String destination, int lineNumber, long journeyID){
        this.destination = destination;
        this.lineNumber = lineNumber;
        this.journeyID = journeyID;
    }
}
