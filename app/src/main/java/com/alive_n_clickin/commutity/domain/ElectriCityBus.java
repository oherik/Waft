package com.alive_n_clickin.commutity.domain;

import lombok.Getter;
import lombok.NonNull;

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
     * @throws NullPointerException if any parameter is null
     */
    public ElectriCityBus(@NonNull String destination, @NonNull String shortName, long journeyID,
                          @NonNull String DGW){
        super(destination, shortName, journeyID);
        this.DGW = DGW;
    }
}
