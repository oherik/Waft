package com.alive_n_clickin.waft.application.api;

import com.alive_n_clickin.waft.domain.IElectriCityBus;
import com.alive_n_clickin.waft.domain.IFlag;
import com.alive_n_clickin.waft.infrastructure.api.response.ConnectionException;

import java.util.List;

/**
 * An interface for adapters between the Waft API and our domain.
 *
 * @since 0.2
 */
public interface IWaftAdapter {
    /**
     * Returns a list of flags for a specific vehicle.
     *
     * @param journeyId the id to get flags for.
     * @return a list of IFlags. If there are no flags on the vehicle the list is empty.
     * @throws ConnectionException if anything goes wrong with the request.
     */
    List<IFlag> getFlagsForVehicle(String journeyId) throws ConnectionException;

    /**
     * Flags the specified bus with the given flag.
     *
     * @param bus the bus object to get flagged.
     * @param flag the flag object to flag the bus with
     * @return true if sending the flag successfully or false if not.
     * @throws ConnectionException if anything goes wrong with the request.
     */
    boolean flagBus(IElectriCityBus bus, IFlag flag) throws ConnectionException;

    /**
     * Remove the given flag completely.
     *
     * @param flag the flag to remove
     * @return true if successful or false if failed.
     * @throws ConnectionException if anything goes wrong with the request.
     */
    boolean deleteFlag(IFlag flag) throws ConnectionException;
}
