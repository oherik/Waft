package com.alive_n_clickin.waft.application.api;


import com.alive_n_clickin.waft.domain.IJourney;
import com.alive_n_clickin.waft.infrastructure.api.ConnectionException;

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
     * @throws ConnectionException if anything goes wrong with the request.
     */
    IJourney getCurrentJourney(String dgw) throws ConnectionException;
}
