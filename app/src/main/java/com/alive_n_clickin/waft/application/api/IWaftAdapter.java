package com.alive_n_clickin.waft.application.api;

import com.alive_n_clickin.waft.domain.IElectriCityBus;
import com.alive_n_clickin.waft.domain.IFlag;

import java.net.SocketTimeoutException;
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
     */
    List<IFlag> getFlagsForVehicle(String journeyId) throws SocketTimeoutException;

    /**
     * Flags the specified bus with the given flag.
     *
     * @param bus the bus object to get flagged.
     * @param flag the flag object to flag the bus with
     * @return true if sending the flag successfully or false if not.
     */
    boolean flagBus(IElectriCityBus bus, IFlag flag);

    /**
     * Remove the given flag completely.
     *
     * @param flag the flag to remove
     * @return true if successful or false if failed.
     */
    boolean deleteFlag(IFlag flag);
}
