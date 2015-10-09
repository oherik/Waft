package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.ElectriCityBus;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IElectricityAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.Journey;

/**
 * This is a factory for creating ready-to-use bus objects. The idea is that you ask this factory
 * for the bus belonging to an id, and that the factory simply responds with a bus object. The factory
 * is responsible for fetching all the data required for that bus from whatever sources it needs.
 */
public class BusFactory {

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
            journeyId = journey.getJourneyId();
        }
        return new ElectriCityBus(destination, "55", journeyId, dgw);
    }
}
