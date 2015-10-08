package com.alive_n_clickin.commutity.infrastructure.api;

import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

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

    @Override
    public JourneyInfo getJourneyInfo(String dgw) {
        String apiResponse = getJourneyInfoFromApi(dgw);

        JsonJavaConverter<JourneyInfo> converter = new JsonJavaConverter<>(JourneyInfo.class);
        List<JourneyInfo> infoList = converter.toJavaList(apiResponse);
        return null;
    }

    private String getJourneyInfoFromApi(String dgw) {
        ElectricityApiConnection apiConn = new ElectricityApiConnection();
        //End time: right now
        long t2 = System.currentTimeMillis();
        //Start time, 30 seconds ago, so that we have some margin
        long t1 = t2 - 30 * 1000;
        String query = "dgw=" + dgw + "&sensorSpec=Ericsson$Journey_Info" +
                "&t1=" + t1 + "&t2=" + t2;
        String response = apiConn.sendGetToElectricity(query);
        Log.d(LogUtils.getLogTag(this), response);
        return response;
    }
}
