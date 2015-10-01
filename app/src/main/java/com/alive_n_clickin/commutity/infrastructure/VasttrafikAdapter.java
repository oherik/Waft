package com.alive_n_clickin.commutity.infrastructure;

import android.net.Uri;
import android.util.Log;

import java.util.List;


public class VasttrafikAdapter implements IVasttrafikAdapter {
    private final VasttrafikApiConnection vasttrafikApiConnection = new VasttrafikApiConnection();


    @Override
    public List<Stop> getNearbyStations(double longitude, double latitude) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.nearbystops",
                "&originCoordLat=" + latitude + "&originCoordLong=" + longitude);

        /*Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(response).getAsJsonObject();
        LocationList locationList = gson.fromJson(obj.get("LocationList"), LocationList.class);*/
        //return locationList.getStopLocations();
        return new JsonJavaConverter<LocationList>(LocationList.class).toJava(
                response,"LocationList").getStopLocations();
    }

    /**
     * This functions gives you a list of stops related to the search string you provide.
     * @param searchString
     * @returns a list of stops if the search was successful.
     * Otherwise returns null since there was no result.
     */
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
