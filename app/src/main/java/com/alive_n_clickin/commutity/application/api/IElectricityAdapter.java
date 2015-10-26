package com.alive_n_clickin.commutity.application.api;


import com.alive_n_clickin.commutity.domain.IJourney;

/**
 * An interface for adapters between the ElectriCity API and our domain.
 *
 * @since 0.1
 */
public interface IElectricityAdapter {
    /**
     * Returns the current journey for a bus.
     *
     * @param dgw the DGW of the bus to get the journey of.
     * @return a journey object with information about the current journey of the bus.
     */
    IJourney getCurrentJourney(String dgw);
}
