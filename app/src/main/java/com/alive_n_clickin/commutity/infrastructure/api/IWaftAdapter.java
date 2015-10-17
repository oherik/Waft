package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonFlag;

import java.util.List;

/**
 * This interface represent the possible request to our backend combined with their responses.
 *
 * @since 0.2
 */
public interface IWaftAdapter {

    /**
     * @param journeyId the id to get flags for.
     * @return a list of IFlags. If there are no flags the list is empty.
     */
    List<JsonFlag> getFlagsForVehicle(String journeyId);

    /**
     * Flags the specified bus with the given flag.
     * @param bus The bus object to get flagged.
     * @param flag The flag object to flag the bus with
     * @return true if sending the flag successfully or false if not.
     */
    boolean flagBus(IElectriCityBus bus, IFlag flag);
}
