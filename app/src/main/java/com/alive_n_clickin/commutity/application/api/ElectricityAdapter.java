package com.alive_n_clickin.commutity.application.api;

import com.alive_n_clickin.commutity.infrastructure.api.ElectricityApiConnection;
import com.alive_n_clickin.commutity.infrastructure.api.JsonJavaConverter;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourney;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourneyInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is not meant to be instantiated. The reason is to remove as much coupling as possible.
 * Use the ApiAdapterFactory to gain access to this class. {@link ApiAdapterFactory}
 *
 * This class represents high level methods that crate suitable request string, which are
 * then passed along to {@link ElectricityApiConnection}
 *
 * @since 0.1
 */
class ElectricityAdapter implements IElectricityAdapter {

    public static final String RESOURCE_SPEC_DESTINATION = "Destination_Value";
    public static final String RESOURCE_SPEC_JOURNEY_ID = "Journey_Name_Value";

    /**
     * The current journey, with id and destination, for the bus with the given DGW
     * @param dgw id of the bus we are looking for
     * @return JsonJourney object with journey id and destination if there was a valid response, null otherwise
     */
    @Override
    public JsonJourney getJourneyInfo(String dgw) {
        String apiResponse = getJourneyInfoFromApi(dgw);
        //Retrieve response as Java object
        List<JsonJourneyInfo> infoList = JsonJavaConverter.toJavaList(apiResponse, JsonJourneyInfo[].class);

        //We did not get any journey data
        if (infoList == null) {
            return null;
        }

        //Sort the list according to timestamps, we only want the most current ones
        Collections.sort(infoList, new Comparator<JsonJourneyInfo>() {

            @Override
            public int compare(JsonJourneyInfo lhs, JsonJourneyInfo rhs) {
                //We want a reverse sort: latest first
                if (rhs.getTimestamp() > lhs.getTimestamp()) {
                    return 1;
                } else {
                    return rhs.getTimestamp() == lhs.getTimestamp() ? 0 : -1;
                }
            }
        });

        //We want the latest values for destination and journeyid. Pick these out and put them in
        //a JsonJourney-object. We get the first object with destination, then break. Same with journey id
        String destination = null;
        String journeyId = null;
        for (JsonJourneyInfo info : infoList) {
            if (info.getResourceSpec().equals(RESOURCE_SPEC_DESTINATION)) {
                destination = info.getValue();
                break;
            }
        }
        for (JsonJourneyInfo info : infoList) {
            if (info.getResourceSpec().equals(RESOURCE_SPEC_JOURNEY_ID)) {
                journeyId = info.getValue();
                break;
            }
        }

        JsonJourney ret = new JsonJourney(destination, journeyId);

        return ret;
    }

    private String getJourneyInfoFromApi(String dgw) {
        ElectricityApiConnection apiConn = new ElectricityApiConnection();

        // End time: right now
        long endTime = System.currentTimeMillis();

        // Start time, 30 seconds ago, so that we have some margin
        long startTime = endTime - 30 * 1000;

        String query = "dgw=" + dgw + "&sensorSpec=Ericsson$Journey_Info" +
                "&t1=" + startTime + "&t2=" + endTime;
        String response = apiConn.sendGetToElectricity(query);
        return response;
    }
}
