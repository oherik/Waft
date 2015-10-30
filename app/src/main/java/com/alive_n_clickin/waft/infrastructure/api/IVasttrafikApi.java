package com.alive_n_clickin.waft.infrastructure.api;

import com.alive_n_clickin.waft.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.waft.infrastructure.api.response.JsonStop;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 * An interface for Västtrafiks API.
 *
 * @since 1.0
 */
public interface IVasttrafikApi {
    /**
     * Returns a list of stops that matches a given search string.
     *
     * @param searchString the string to search for.
     * @return a list of stops that matches the given search string. If no stops matches, an empty
     * list is returned. If anything goes wrong with the request, null is returned.
     */
    List<JsonStop> searchForStops(String searchString) throws SocketTimeoutException;

    /**
     * Returns a list of arrivals for a stop.
     *
     * @param id the id of the stop to get arrivals to.
     * @return a list of arrivals for a stop. If no arrivals can be found for the stop, an empty
     * list is returned.  If anything goes wrong with the request, null is returned.
     */
    List<JsonArrival> getArrivalsForStop(long id) throws SocketTimeoutException;
}
