package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.infrastructure.api.response.JsonFlag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A concrete implementation of IWaftApi.
 */
class WaftApi implements IWaftApi {
    private final WaftApiConnection waftApiConnection = new WaftApiConnection();

    @Override
    public List<JsonFlag> getFlagsForJourney(String journeyId) {
        String response = this.waftApiConnection.sendGetToWaft("flags", journeyId);

        List<JsonFlag> flags = new ArrayList<>();

        if (response != null) {
            flags = JsonJavaConverter.toJavaList(response, JsonFlag[].class);
        }

        return flags;
    }

    @Override
    public boolean addFlag(String dgw, String journeyId, int flagTypeId, String comment, Date createdTime) {
        String query = "flagType=" + flagTypeId +
                "&comment=" + comment +
                "&time=" + createdTime.toString() +
                "&dgw=" + dgw +
                "&journeyID=" + journeyId;

        int statusCode = this.waftApiConnection.sendPostToWaft("flags", query);
        return interpretResult(statusCode);
    }

    @Override
    public boolean deleteFlag(String id) {
        int statusCode = this.waftApiConnection.sendDeleteToWaft(id);
        return interpretResult(statusCode);
    }

    /**
     * @param statusCode
     * @return true if 200 otherwise false.
     */
    private boolean interpretResult(int statusCode) {
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
}
