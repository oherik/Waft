package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.Bus;
import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IElectricityAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.JourneyInfo;

import java.util.ArrayList;

// TODO: Documentation
public class BusFactory {

    // TODO: Documentation
    public static IBus getBus(String DGW) {
        // TODO: Build bus from real data
        // 1. get information from ElectriCity's API and our backend
        // 2. create and return bus object

        IElectricityAdapter ecAdapter = ApiAdapterFactory.createElectricityAdapter();
        JourneyInfo ji = ecAdapter.getJourneyInfo("Ericsson$100021");
        return new Bus(DGW, "destination", "journeyName", "routeNumber", new ArrayList<IFlag>());
    }
}
