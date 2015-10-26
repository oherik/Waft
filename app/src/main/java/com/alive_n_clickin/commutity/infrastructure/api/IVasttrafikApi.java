package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;

import java.util.List;

/**
 * An interface for Västtrafiks API.
 */
public interface IVasttrafikApi {
    /**
     * Returns a list of stops near a coordinate.
     *
     * @param longitude the longitude of the coordinate.
     * @param latitude the latitude of the coordinate.
     * @return a list of stops near a coordinate. If no stops are near, an empty list is returned.
     */
    List<JsonStop> getNearbyStops(double longitude, double latitude);

    /**
     * Returns a list of stops that matches a given search string.
     *
     * @param searchString the string to search for.
     * @return a list of stops that matches the given search string. If no stops matches, an empty
     * list is returned.
     */
    List<JsonStop> searchForStops(String searchString);

    /**
     * Returns a list of arrivals for a stop.
     *
     * @param id the id of the stop to get arrivals to.
     * @return a list of arrivals for a stop. If no arrivals can be found for the stop, an empty
     * list is returned.
     */
    List<JsonArrival> getArrivalsForStop(long id);
}
