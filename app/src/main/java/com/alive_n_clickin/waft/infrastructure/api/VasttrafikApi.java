package com.alive_n_clickin.waft.infrastructure.api;

import android.net.Uri;

import com.alive_n_clickin.waft.Config;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonArrivalList;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonStop;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonStopList;
import com.alive_n_clickin.waft.infrastructure.api.response.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A concrete implementation of IVasttrafikApi.
 *
 * @since 1.0
 */
class VasttrafikApi implements IVasttrafikApi {
    private static final String BASE_URL = "http://api.vasttrafik.se/bin/rest.exe/v1";
    private static final String API_KEY = Config.VASTTRAFIK_API_KEY;

    @Override
    public List<JsonStop> searchForStops(String searchString) {
        Response response = sendGet("/location.name?input=" + Uri.encode(searchString));

        if (response == null) {
            return new ArrayList<>();
        }

        JsonStopList jsonStopList = new JsonJavaConverter<>(JsonStopList.class).toJava(
                response.getBody(), "LocationList");

        if (jsonStopList == null) {
            return new ArrayList<>();
        }

        // The api returns results that begin with "." that are not relevant to our implementation.
        // We must filter this out. That is what the for loop does. (It's a filter)
        List<JsonStop> jsonStops = new ArrayList<>();

        for (JsonStop jsonStop : jsonStopList.getStopLocations()) {
            if (!jsonStop.getName().startsWith(".")) {
                jsonStops.add(jsonStop);
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
        Response response = sendGet("/departureBoard?id=" + id + "&date=" + date + "&time=" + time);

        if (response == null) {
            return new ArrayList<>();
        }

        JsonArrivalList jsonArrivalList = new JsonJavaConverter<>(JsonArrivalList.class).toJava(
                response.getBody(), "DepartureBoard");

        return jsonArrivalList.getDepartures();
    }

    private static String buildUrl(String query) {
        return BASE_URL + query + "&authKey=" + API_KEY + "&format=json";
    }

    private static Response sendGet(String query) {
        String url = buildUrl(query);
        return ApiConnection.get(url);
    }
}
