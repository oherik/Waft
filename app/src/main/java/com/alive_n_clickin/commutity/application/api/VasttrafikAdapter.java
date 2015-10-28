package com.alive_n_clickin.commutity.application.api;

import com.alive_n_clickin.commutity.application.VehicleFactory;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.domain.Stop;
import com.alive_n_clickin.commutity.infrastructure.api.ApiFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IVasttrafikApi;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;

import java.util.ArrayList;
import java.util.List;

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
            arrivingVehicles.add(VehicleFactory.getArrivingVehicle(jsonArrival));
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
}
