package com.alive_n_clickin.commutity.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A class modelling the ElectriCity bus. It contains an unique DGW ID.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ElectriCityBus extends AbstractVehicle implements IElectriCityBus {
    @Getter private final String DGW;

    /**
     * Constructor
     * @param destination Where the vehicle is headed, e.g. "Sahlgrenska"
     * @param shortRouteName    The short version of the route name, usually simply a line number
     *                          (e.g. "55"), but can also be longer, e.g. "Grön Express"
     * @param journeyID from Vasttrafik. This is the unique identification number for a certain
     *                  route. It gets changed any time the vehicle arrives to the end stop and
     *                  continues in the opposite direction.
     * @param DGW from ElectriCity
     * @throws NullPointerException if any parameter is null
     */
    public ElectriCityBus(@NonNull String destination, @NonNull String shortRouteName, String journeyID,
                          @NonNull String DGW){
        super(destination, shortRouteName, journeyID);
        this.DGW = DGW;
    }
}
