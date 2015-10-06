package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.List;


public interface IWaftAdapter {

    /**
     * @param bus The bus object to get current flags on
     * @return a list of IFlags. If there are no flags the list is empty.
     */
    List<IFlag> getFlagsForBus(IBus bus);

    /**
     * Flags the specified bus with the given flag.
     * @param bus The bus object to get flagged.
     * @param flag The flag object to flag the bus with
     */
    void flagBus(IBus bus,IFlag flag);
}
