package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;

import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrivalList;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStopList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A concrete implementation of IVasttrafikApi.
 */
class VasttrafikApi implements IVasttrafikApi {
    private static final VasttrafikApiConnection vasttrafikApiConnection = new VasttrafikApiConnection();

    @Override
    public List<JsonStop> getNearbyStops(double longitude, double latitude) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.nearbystops",
                "&originCoordLat=" + latitude + "&originCoordLong=" + longitude);

        List<JsonStop> stops = new ArrayList<>();

        if (response != null) {
            JsonStopList jsonStopList = new JsonJavaConverter<>(JsonStopList.class).toJava(
                    response, "LocationList");
            stops = jsonStopList.getStopLocations();
        }

        return stops;
    }

    @Override
    public List<JsonStop> searchForStops(String searchString) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik("location.name",
                "&input=" + Uri.encode(searchString));

        List<JsonStop> jsonStops = new ArrayList<>();

        if (response != null) {
            Object responseObject = new JsonJavaConverter<>(JsonStopList.class).toJava(
                    response, "LocationList");

            if (responseObject != null) {
                // The api returns results that begin with "." that are not relevant to our implementation.
                // We must filter this out. That is what the for loop does. (It's a filter)
                JsonStopList locationList = (JsonStopList)responseObject;

                for (JsonStop jsonStop : locationList.getStopLocations()) {
                    if (!jsonStop.getName().startsWith(".")) {
                        jsonStops.add(jsonStop);
                    }
                }
            }
        }

        return jsonStops;
    }

    @Override
    public List<JsonArrival> getArrivalsForStop(long id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date dateAndTime = new Date();
        String date = dateFormat.format(dateAndTime);
        String time = timeFormat.format(dateAndTime);

        // Since no maximum number of vehicles has been set, the API will return the 20 first.
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "departureBoard",
                "&id=" + id + "&date=" + date + "&time=" + time);

        List<JsonArrival> jsonArrivals = new ArrayList<>();

        if (response != null) {
            JsonArrivalList jsonArrivalList = new JsonJavaConverter<>(JsonArrivalList.class).toJava(
                    response, "DepartureBoard");

            jsonArrivals = jsonArrivalList.getDepartures();
        }

        return jsonArrivals;
    }
}
