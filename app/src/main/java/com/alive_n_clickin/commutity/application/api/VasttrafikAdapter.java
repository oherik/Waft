package com.alive_n_clickin.commutity.application.api;

import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.domain.Stop;
import com.alive_n_clickin.commutity.infrastructure.api.ApiFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IVasttrafikApi;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.NonNull;

/**
 * {@inheritDoc}<br><br>
 *
 * This class is not meant to be instantiated by outside classes (hence the package private access).
 *
 * This class uses IVasttrafikApi to get response objects, and then transforms them to to domain
 * objects.
 *
 * @since 0.1
 */
class VasttrafikAdapter implements IVasttrafikAdapter {

    private static final String ELECTRICITY_SHORT_ROUTE_NAME = "55";
    private final IVasttrafikApi vasttrafikApi = ApiFactory.createVasttrafikApi();

    private IStop convertStop(JsonStop jsonStop) {
        return new Stop(jsonStop.getName(), jsonStop.getId());
    }

    private List<IStop> convertStops(List<JsonStop> jsonStops) {
        List<IStop> stops = new ArrayList<>();
        for (JsonStop jsonStop : jsonStops) {
            stops.add(convertStop(jsonStop));
        }
        return stops;
    }

    private List<IArrivingVehicle> convertArrivals(List<JsonArrival> jsonArrivals) {
        List<IArrivingVehicle> arrivingVehicles = new ArrayList<>();
        for (JsonArrival jsonArrival : jsonArrivals) {
            arrivingVehicles.add(convertArrival(jsonArrival));
        }
        return arrivingVehicles;
    }

    @Override
    public List<IStop> searchForStops(String searchString) {
        List<JsonStop> jsonStops = vasttrafikApi.searchForStops(searchString);
        return convertStops(jsonStops);
    }

    @Override
    public List<IArrivingVehicle> getArrivalsForStop(IStop stop) {
        List<JsonArrival> jsonArrivals = vasttrafikApi.getArrivalsForStop(stop.getId());
        return convertArrivals(jsonArrivals);
    }

    /**
     * Creates a new arriving vehicle object based on an API response.
     *
     * @param jsonArrival The API response
     * @return  A new arriving vehicle based on the response
     * @throws NullPointerException if the parameter is null
     */
    private IArrivingVehicle convertArrival(@NonNull JsonArrival jsonArrival) {
        String direction = jsonArrival.getDirection();
        String shortRouteName = jsonArrival.getSname();
        String journeyId = jsonArrival.getJourneyid();
        Date realArrival = jsonArrival.getRealArrival();
        int lineColor = jsonArrival.getLineColor();

        IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
        List<IFlag> flags = new LinkedList<>();
        if (shortRouteName.equals(ELECTRICITY_SHORT_ROUTE_NAME)) {
            flags = waftAdapter.getFlagsForVehicle(journeyId);
        }

        return new ArrivingVehicle(direction, shortRouteName, journeyId, realArrival, flags, lineColor);
    }
}
