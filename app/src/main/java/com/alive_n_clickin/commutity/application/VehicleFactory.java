package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.ElectriCityBus;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IElectricityAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.IWaftAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonFlag;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonJourney;

import java.util.ArrayList;
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

    private final static String CLASS_NUMBER = "9015";
    private final static String THM_NUMBER = "014";
    private final static String ELECTRICITY_LINE_NUMBER = "5055";
    private final static String ELECTRICITY_JOURNEY_ID_PREFIX = CLASS_NUMBER + THM_NUMBER + ELECTRICITY_LINE_NUMBER;
    public static final String ELECTRICITY_SHORT_ROUTE_NAME = "55";

    /**
     * Takes a dgw and returns a new bus object with all the data for the bus with that DGW.
     *
     * This method may not be called from the UI thread.
     *
     * @param dgw the dgw id for the bus you want to have.
     * @return a new bus object.
     */
    public static IElectriCityBus getElectriCityBus(String dgw) {
        IElectricityAdapter ecAdapter = ApiAdapterFactory.createElectricityAdapter();
        JsonJourney jsonJourney = ecAdapter.getJourneyInfo(dgw);
        String destination = "";
        String journeyId = "";
        if (jsonJourney != null) {
            destination = jsonJourney.getDestination();
            journeyId = ELECTRICITY_JOURNEY_ID_PREFIX + padWithZeroes(jsonJourney.getJourneyId(), 5);
        }
        List<IFlag> flags = new ArrayList<>();

        IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
        List<JsonFlag> jsonFlagList = waftAdapter.getFlagsForVehicle(journeyId);
        if(jsonFlagList != null) {
            flags.addAll(FlagFactory.getFlags(jsonFlagList));
        }

        return new ElectriCityBus(destination, journeyId, dgw, flags);
    }

    /**
     * Pads a string with zeroes.
     *
     * @param string the string to pad.
     * @param wantedLength the wanted length of the returned string
     * @return a new string consisting of the sent in string padded with zeroes so that it's size
     * equals wantedLength.
     */
    private static String padWithZeroes(String string, int wantedLength) {
        String zeroes = "";
        for (int i = 0; i < wantedLength; i++) {
            zeroes += "0";
        }

        String paddedString = zeroes + string;
        return paddedString.substring(paddedString.length() - wantedLength, paddedString.length());
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

        List<IFlag> flags = new LinkedList<>();
        if (shortRouteName.equals(ELECTRICITY_SHORT_ROUTE_NAME)) {
            IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
            List<JsonFlag> jsonFlags = waftAdapter.getFlagsForVehicle(journeyId);
            flags = FlagFactory.getFlags(jsonFlags);
        }

        return new ArrivingVehicle(direction, shortRouteName, journeyId, realArrival, flags);
    }
}
