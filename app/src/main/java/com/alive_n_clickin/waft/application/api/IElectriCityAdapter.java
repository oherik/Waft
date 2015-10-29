package com.alive_n_clickin.waft.application.api;


import com.alive_n_clickin.waft.domain.IJourney;

/**
 * An interface for adapters between the ElectriCity API and our domain.
 *
 * @since 0.1
 */
public interface IElectriCityAdapter {
    /**
     * Returns the current journey for a bus.
     *
     * @param dgw the DGW of the bus to get the journey of.
     * @return a journey object with information about the current journey of the bus. If the bus
     * is not currently on a journey, and empty IJourney object is returned.
     */
    IJourney getCurrentJourney(String dgw);
}
