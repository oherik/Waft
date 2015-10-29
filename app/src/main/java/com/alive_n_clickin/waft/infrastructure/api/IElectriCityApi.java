package com.alive_n_clickin.waft.infrastructure.api;

import com.alive_n_clickin.waft.infrastructure.api.response.JsonJourney;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonJourneyInfo;

import java.util.List;

/**
 * An interface for ElectriCity's API.
 */
public interface IElectriCityApi {
    /**
     * Returns the latest journey for a bus.
     *
     * @param dgw the DGW for the bus to get the latest journey for.
     * @return a journey object containing the latest journey information from the API for the
     * specified bus. If no journey info is found, an empty JsonJourney object is returned.
     */
    JsonJourney getLatestJourney(String dgw);

    /**
     * Returns all recorded journey info between two timestamps for a bus.
     *
     * @param dgw the DGW for the bus to get journey info for.
     * @param startTime the start time. Number of milliseconds since epoch.
     * @param endTime the end time. Number of milliseconds since epoch.
     * @return the latest journey info the API has for the specified bus. If no journey info is found,
     * it returns an empty list.
     */
    List<JsonJourneyInfo> getJourneyInfo(String dgw, long startTime, long endTime);
}
