package com.alive_n_clickin.commutity.domain;

/**
 * An interface for ElectriCity buses. ElectriCity buses should be able to provide a DGW.
 */
public interface IElectriCityBus extends IVehicle {
    /**
     * @return the unique DGW that identifies this ElectriCity bus.
     */
    String getDGW();
}
