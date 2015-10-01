package com.alive_n_clickin.commutity.infrastructure;

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

    public List<Stop> getSearchStops(String searchString) {
        String response = vasttrafikApiConnection.sendGetToVasttrafik(
                "location.name",
                "&input=" + searchString
        );
        /*
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(response).getAsJsonObject();
        LocationList locationList = gson.fromJson(obj.get("LocationList"), LocationList.class);


        return locationList.getStopLocations();*/
        return new JsonJavaConverter<LocationList>(LocationList.class).toJava(
                response,"LocationList").getStopLocations();
    }
}
