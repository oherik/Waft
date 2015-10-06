package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.Bus;
import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.ArrayList;

// TODO: Documentation
public class BusFactory {

    // TODO: Documentation
    public static IBus getBus(String DGW) {
        // TODO: Build bus from real data
        // 1. map bssid to other id:s
        // 2. get information from ElectriCity's API and our backend
        // 3. create and return bus object

        return new Bus(DGW  , "destination", "journeyName", "routeNumber", new ArrayList<IFlag>());
    }
}
