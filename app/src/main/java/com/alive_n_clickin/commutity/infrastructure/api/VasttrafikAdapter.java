package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public List<Stop> getNearbyStations(double longitude, double latitude) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.nearbystops",
                "&originCoordLat=" + latitude + "&originCoordLong=" + longitude);
        if(response != null){
            return new JsonJavaConverter<LocationList>(LocationList.class).toJava(
                    response,"LocationList").getStopLocations();
        } else {
            return null;
        }

    }

    @Override
    public List<Stop> getSearchStops(String searchString) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.name",
                "&input=" + Uri.encode(searchString)
        );
        if(response != null){
            Object stopList = new JsonJavaConverter<LocationList>(LocationList.class).toJava(
                    response, "LocationList");
            if(stopList != null){
                LocationList a = (LocationList)stopList;
                return a.getStopLocations();
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
    public List<ArrivingVehicle> getVehiclesHeadedToStop(Stop stop) {
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
            List<ArrivingVehicle> test = new ArrayList<>();
             return new JsonJavaConverter<ArrivalList>(ArrivalList.class).toJava(
                    response, "DepartureBoard").getDeparture();
        }
        return null;
    }
}
