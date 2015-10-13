package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.ElectriCityBus;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.JsonFlag;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IElectricityAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.IWaftAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.Journey;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;

import java.util.Date;
import java.util.List;

import lombok.NonNull;

/**
 * This is a factory for creating ready-to-use vehicles. The idea is that you ask this factory
 * to build a vehicle from existing data (for example an ID or a response object received from
 * an external API), and that the factory simply responds with a vehicle. The factory
 * is responsible for fetching all the data required for that vehicle from whatever sources it needs.
 */
public class VehicleFactory {

    private final static String CLASS_NUMBER = "9015";
    private final static String THM_NUMBER = "014";
    private final static String ELECTRICITY_LINE_NUMBER = "5055";
    private final static String ELECTRICITY_JOURNEY_ID_PREFIX = CLASS_NUMBER + THM_NUMBER + ELECTRICITY_LINE_NUMBER;

    /**
     * Takes a dgw and returns a new bus object with all the data for the bus with that DGW.
     *
     * This method may not be called from the UI thread.
     *
     * @param dgw the dgw id for the bus you want to have.
     * @return a new bus object.
     */
    public static IElectriCityBus getBus(String dgw) {
        IElectricityAdapter ecAdapter = ApiAdapterFactory.createElectricityAdapter();
        Journey journey = ecAdapter.getJourneyInfo(dgw);
        String destination = "";
        String journeyId = "";
        if (journey != null) {
            destination = journey.getDestination();
            journeyId = ELECTRICITY_JOURNEY_ID_PREFIX + padWithZeroes(journey.getJourneyId(), 5);
        }
        return new ElectriCityBus(destination, journeyId, dgw);
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
    public static IArrivingVehicle getArrivingVehicle(@NonNull JsonArrival jsonArrival) {
        String direction = jsonArrival.getDirection();
        String shortName = jsonArrival.getSname();
        String journeyId = jsonArrival.getJourneyid();
        Date realArrival = jsonArrival.getRealArrival();

        IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
        List<JsonFlag> jsonFlags = waftAdapter.getFlagsForVehicle(journeyId);
        List<IFlag> flags = FlagFactory.getFlags(jsonFlags);

        IArrivingVehicle arrivingVehicle = new ArrivingVehicle(direction, shortName, journeyId, realArrival, flags);
        return arrivingVehicle;
    }
}
