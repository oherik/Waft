package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.application.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.application.api.IElectriCityAdapter;
import com.alive_n_clickin.commutity.application.api.IWaftAdapter;
import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.ElectriCityBus;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IJourney;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.NonNull;

/**
 * This is a factory for creating ready-to-use vehicles. The idea is that you ask this factory
 * to build a vehicle from existing data (for example an ID or a response object received from
 * an external API), and that the factory simply responds with a vehicle. The factory
 * is responsible for fetching all the data required for that vehicle from whatever sources it needs.
 *
 * @since 0.2
 */
public class VehicleFactory {
    private static final String ELECTRICITY_SHORT_ROUTE_NAME = "55";

    private static final IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
    private static final IElectriCityAdapter electriCityAdapter = ApiAdapterFactory.createElectricityAdapter();

    /**
     * Takes a dgw and returns a new bus object with all the data for the bus with that DGW.
     *
     * This method may not be called from the UI thread.
     *
     * @param dgw the dgw id for the bus you want to have.
     * @return a new bus object.
     */
    public static IElectriCityBus getElectriCityBus(String dgw) {
        IJourney journey = electriCityAdapter.getCurrentJourney(dgw);

        String destination = journey.getDestination();
        String journeyId = journey.getJourneyId();

        List<IFlag> flags = waftAdapter.getFlagsForVehicle(journeyId);

        return new ElectriCityBus(destination, journeyId, dgw, flags);
    }

    /**
     * Creates a new arriving vehicle object based on an API response.
     *
     * @param jsonArrival The API response
     * @return  A new arriving vehicle based on the response
     * @throws NullPointerException if the parameter is null
     */
    /* TODO Create method for creating a list of arriving vehicles, that loads all flags at once.
    Then we don't have to rely on this method for creating every vehicle in a list. Instead we can
    fetch all flags for the relevant lines at once from the api, thus reducing the number of api calls.
    */
    public static IArrivingVehicle getArrivingVehicle(@NonNull JsonArrival jsonArrival) {
        String direction = jsonArrival.getDirection();
        String shortRouteName = jsonArrival.getSname();
        String journeyId = jsonArrival.getJourneyid();
        Date realArrival = jsonArrival.getRealArrival();
        int lineColor = jsonArrival.getLineColor();

        List<IFlag> flags = new LinkedList<>();
        if (shortRouteName.equals(ELECTRICITY_SHORT_ROUTE_NAME)) {
            flags = waftAdapter.getFlagsForVehicle(journeyId);
        }

        return new ArrivingVehicle(direction, shortRouteName, journeyId, realArrival, flags, lineColor);
    }
}
