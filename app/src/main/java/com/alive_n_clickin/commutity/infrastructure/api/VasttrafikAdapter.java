package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

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
}
