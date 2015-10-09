package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.List;


public interface IWaftAdapter {

    /**
     * @param journeyId the id to get flags for.
     * @return a list of IFlags. If there are no flags the list is empty.
     */
    List<IFlag> getFlagsForVehicle(int journeyId);

    /**
     * Flags the specified bus with the given flag.
     * @param bus The bus object to get flagged.
     * @param flag The flag object to flag the bus with
     */
    void flagBus(IElectriCityBus bus,IFlag flag);
}
