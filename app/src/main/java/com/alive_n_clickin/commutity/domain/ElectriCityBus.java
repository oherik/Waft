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
     * @param shortName
     * @param journeyID from Vasttrafik
     * @param DGW from ElectriCity
     */
    public ElectriCityBus(String destination, String shortName, long journeyID, String DGW) {
        super(destination, shortName, journeyID);
        this.DGW = DGW;
    }
}
