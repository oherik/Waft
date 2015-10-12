package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.JsonFlag;

import java.util.List;

/**
 * This class is not meant to be instantiated. The reason is to remove as much coupling as possible.
 * Use the ApiAdapterFactory to gain access to this class.
 *
 * This class represents high level methods for connection to the Waft API.
 */
class WaftAdapter implements IWaftAdapter {
    private final WaftApiConnection waftApiConnection = new WaftApiConnection();

    @Override
    public List<JsonFlag> getFlagsForVehicle(int journeyId) {
        String response = waftApiConnection.sendGetToWaft("flags", "" + journeyId);
        if (response != null) {
            return JsonJavaConverter.toJavaList(response, JsonFlag[].class);
        }
        return null;
    }

    @Override
    public boolean flagBus(IElectriCityBus bus, IFlag flag) {
        int statusCode = waftApiConnection.sendPostToWaft(
                "flags",
                getFormattedPostFlagString(bus, flag)
        );
        switch (statusCode) {
            case -1:
                return false;
            case 400:
                return false;
            case 200:
                return true;
        }
        return false;
    }

    private String getFormattedPostFlagString(IElectriCityBus bus, IFlag flag) {
        //TODO: Check whether or not the parameters are added properly
        String query = "flagType=" + flag.getType().getId() +
                "&comment=" + flag.getComment() +
                "&time=" + flag.getCreatedTime().toString() +
                "&dgw=" + bus.getDGW() +
                "&journeyID=" + bus.getJourneyID();
        return query;
    }
}
