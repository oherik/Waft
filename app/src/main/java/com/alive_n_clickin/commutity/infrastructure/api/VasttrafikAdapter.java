package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrivalList;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStopList;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is not meant to be instantiated. The reason is to remove as much coupling as possible.
 * Use the ApiAdapterFactory to gain access to this class. {@link ApiAdapterFactory}
 *
 * This class represents high level methods that crate suitable request string, which are
 * then passed along to {@link VasttrafikApiConnection}
 * @since 0.1
 */
class VasttrafikAdapter implements IVasttrafikAdapter {
    private final VasttrafikApiConnection vasttrafikApiConnection = new VasttrafikApiConnection();


    @Override
    public List<JsonStop> getNearbyStations(double longitude, double latitude) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.nearbystops",
                "&originCoordLat=" + latitude + "&originCoordLong=" + longitude);
        if(response != null){
            return new JsonJavaConverter<JsonStopList>(JsonStopList.class).toJava(
                    response,"LocationList").getStopLocations();
        } else {
            return null;
        }

    }

    @Override
    public List<JsonStop> getSearchStops(String searchString) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.name",
                "&input=" + Uri.encode(searchString)
        );
        if(response != null){
            Object responseObject = new JsonJavaConverter<JsonStopList>(JsonStopList.class).toJava(
                    response, "LocationList");
            if(responseObject != null){
                //The api returns results that begin with "." that are not relevant to our implementation.
                //We must filter this out. That is what the for loop does. (It's a filter)
                JsonStopList locationList = (JsonStopList)responseObject;
                List<JsonStop> jsonStopList = new LinkedList<>();
                for (JsonStop jsonStop : locationList.getStopLocations()) {
                    if (!jsonStop.getName().startsWith(".")) {
                        jsonStopList.add(jsonStop);
                    }
                }
                return jsonStopList;
            } else {
                Log.d("ASD","stopList is null");
                return null;
            }
        } else{
            Log.d("ASD","response from server is null");
            return null;
        }
    }

    @Override
    public List<JsonArrival> getVehiclesHeadedToStop(IStop stop) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date dateAndTime = new Date();
        String date = dateFormat.format(dateAndTime);
        String time = timeFormat.format(dateAndTime);

        // Since no maximum number of vehicles has been set, the API will return the 20 first.
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "departureBoard",
                        "&id=" + stop.getId() +
                        "&date=" + date +
                        "&time=" + time);
        if(response != null){
             return new JsonJavaConverter<>(JsonArrivalList.class).toJava(
                    response, "DepartureBoard").getDeparture();
        }
        return null;
    }
}
