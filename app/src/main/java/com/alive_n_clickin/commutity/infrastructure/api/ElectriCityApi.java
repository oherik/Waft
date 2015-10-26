package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourney;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourneyInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A concrete implementation of IElectriCityApi.
 */
class ElectriCityApi implements IElectriCityApi {
    public static final String RESOURCE_SPEC_DESTINATION = "Destination_Value";
    public static final String RESOURCE_SPEC_JOURNEY_ID = "Journey_Name_Value";

    private final ElectriCityApiConnection electriCityApiConnection = new ElectriCityApiConnection();

    @Override
    public JsonJourney getLatestJourney(String dgw) {
        // End time: right now
        long endTime = System.currentTimeMillis();

        // Start time, 30 seconds ago, so that we have some margin
        long startTime = endTime - 30 * 1000;

        List<JsonJourneyInfo> journeyInfoList = getJourneyInfo(dgw, startTime, endTime);

        // Sort the list according to timestamps, we only want the most current ones
        Collections.sort(journeyInfoList, new Comparator<JsonJourneyInfo>() {
            @Override
            public int compare(JsonJourneyInfo lhs, JsonJourneyInfo rhs) {
                // We want a reverse sort: latest first
                if (rhs.getTimestamp() > lhs.getTimestamp()) {
                    return 1;
                } else {
                    return rhs.getTimestamp() == lhs.getTimestamp() ? 0 : -1;
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
    public List<JsonJourneyInfo> getJourneyInfo(String dgw, long startTime, long endTime) {
        String query = "dgw=" + dgw + "&sensorSpec=Ericsson$Journey_Info" +
                "&t1=" + startTime + "&t2=" + endTime;

        String response = electriCityApiConnection.sendGetToElectricity(query);

        return JsonJavaConverter.toJavaList(response, JsonJourneyInfo[].class);
    }
}
