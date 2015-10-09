package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.IElectriCityBus;
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
    public List<IFlag> getFlagsForBus(IElectriCityBus bus) {
        String response = waftApiConnection.sendGetToWaft("flags","busDGW=" + bus.getDGW());
        if(response != null){
            //TODO: Convert from json to java and return that instead + error handling
        }
        return null;
    }

    @Override
    public void flagBus(IElectriCityBus bus, IFlag flag) {
        waftApiConnection.sendPostToWaft(
                "flags",
                getFormattedPostFlagString(bus, flag)
                );
    }

    private String getFormattedPostFlagString(IElectriCityBus bus, IFlag flag){
        //TODO: Check whether or not the parameters are added properly
        String query = "flagType=" + flag.getType().getId() +
                "&comment=" + flag.getComment() +
                "&time=" + flag.getCreatedTime().toString() +
                "&busDGW" + bus.getDGW();
        return query;
    }
}
