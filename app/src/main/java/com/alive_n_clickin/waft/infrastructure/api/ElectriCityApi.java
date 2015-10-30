package com.alive_n_clickin.waft.infrastructure.api;

import android.util.Log;

import com.alive_n_clickin.waft.Config;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonJourney;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonJourneyInfo;
import com.alive_n_clickin.waft.infrastructure.api.response.Response;
import com.alive_n_clickin.waft.util.LogUtils;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A concrete implementation of IElectriCityApi.
 *
 * @since 1.0
 */
class ElectriCityApi implements IElectriCityApi {
    private static final String LOG_TAG = LogUtils.getLogTag(ElectriCityApi.class);

    private static final String BASE_URL = "https://ece01.ericsson.net:4443/ecity";
    private static final String AUTHORIZATION = Config.ELECTRICITY_AUTHORIZATION;

    public static final String RESOURCE_SPEC_DESTINATION = "Destination_Value";
    public static final String RESOURCE_SPEC_JOURNEY_ID = "Journey_Name_Value";

    @Override
    public JsonJourney getLatestJourney(String dgw) throws ConnectionException {
        // End time: right now
        long endTime = System.currentTimeMillis();

        // Start time, 60 seconds ago, so that we have some margin
        long startTime = endTime - 60 * 1000;

        List<JsonJourneyInfo> journeyInfoList = getJourneyInfo(dgw, startTime, endTime);

        // Sort the list according to timestamps, we only want the most current ones
        Collections.sort(journeyInfoList, new Comparator<JsonJourneyInfo>() {
            @Override
            public int compare(JsonJourneyInfo first, JsonJourneyInfo second) {
                // We want a reverse sort: latest first
                if (second.getTimestamp() > first.getTimestamp()) {
                    return 1;
                } else {
                    return second.getTimestamp() == first.getTimestamp() ? 0 : -1;
                }
            }
        });

        // We want the latest values for destination and journey id. Pick these out and put them in
        // a JsonJourney-object. We get the first object with destination, then break. Same with journey id
        String destination = "";
        String journeyId = "";

        for (JsonJourneyInfo journeyInfo : journeyInfoList) {
            if (journeyInfo.getResourceSpec().equals(RESOURCE_SPEC_DESTINATION)) {
                destination = journeyInfo.getValue();
                break;
            }
        }

        for (JsonJourneyInfo journeyInfo : journeyInfoList) {
            if (journeyInfo.getResourceSpec().equals(RESOURCE_SPEC_JOURNEY_ID)) {
                journeyId = journeyInfo.getValue();
                break;
            }
        }

        return new JsonJourney(destination, journeyId);
    }

    @Override
    public List<JsonJourneyInfo> getJourneyInfo(String dgw, long startTime, long endTime)
            throws ConnectionException {

        String query = "?dgw=" + dgw + "&sensorSpec=Ericsson$Journey_Info" +
                "&t1=" + startTime + "&t2=" + endTime;

        Response response = sendGet(query);

        try {
            return JsonJavaConverter.toJavaList(response.getBody(), JsonJourneyInfo[].class);
        } catch (JsonSyntaxException e) {
            Log.e(LOG_TAG, "Error parsing JSON", e);
            throw new ConnectionException("Error parsing response from server", e);
        }
    }

    private static String buildUrl(String query) {
        return BASE_URL + query;
    }

    private static Response sendGet(String query) throws ConnectionException {
        String url = buildUrl(query);

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Authorization", AUTHORIZATION));

        return ApiConnection.get(url, parameters);
    }
}
