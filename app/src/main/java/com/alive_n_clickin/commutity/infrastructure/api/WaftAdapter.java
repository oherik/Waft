package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.List;

/**
 * This class is not meant to be instantiated. The reason is to remove as much coupling as possible.
 * Use the ApiAdapterFactory to gain access to this class.
 *
 * This class represents high level methods for connection to the Waft API.
 */
class WaftAdapter implements IWaftAdapter{
    private final WaftApiConnection waftApiConnection = new WaftApiConnection();

    @Override
    public List<IFlag> getFlagsForBus(IBus bus) {
        return null;

    }

    @Override
    public void flagBus(IBus bus, IFlag flag) {

    }
}
