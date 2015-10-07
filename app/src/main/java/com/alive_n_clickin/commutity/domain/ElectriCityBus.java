package com.alive_n_clickin.commutity.domain;

import lombok.Getter;

/**
 * A class modelling the ElectriCity bus. It contains an unique DGW ID.
 */
public class ElectriCityBus extends Vehicle{
    @Getter private String DGW;

    /**
     * Constructor
     * @param destination
     * @param lineNumber
     * @param journeyID from Vasttrafik
     * @param DGW from ElectriCity
     */
    public ElectriCityBus(String destination, int lineNumber, long journeyID, String DGW) {
        super(destination, lineNumber, journeyID);
        this.DGW = DGW;
    }
}
