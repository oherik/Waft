package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.Bus;
import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;

import java.util.ArrayList;

/**
 * This is a factory for creating ready-to-use bus objects. The idea is that you ask this factory
 * for the bus belonging to an id, and that the factory simply responds with a bus object. The factory
 * is responsible for fetching all the data required for that bus from whatever sources it needs.
 */
public class BusFactory {

    /**
     * Takes a DGW and returns a new bus object will all the data for the bus with that DGW.
     *
     * @param DGW the DGW id for the bus you want to have.
     * @return a new bus object.
     */
    public static IBus getBus(String DGW) {
        // TODO: Build bus from real data
        // 1. map bssid to other id:s
        // 2. get information from ElectriCity's API and our backend
        // 3. create and return bus object

        return new Bus(DGW, "destination", "journeyName", "routeNumber", new ArrayList<IFlag>());
    }
}
