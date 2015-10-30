package com.alive_n_clickin.waft.application.api;

import com.alive_n_clickin.waft.domain.IArrivingVehicle;
import com.alive_n_clickin.waft.domain.IStop;
import com.alive_n_clickin.waft.infrastructure.api.ConnectionException;

import java.util.List;

/**
 * An interface for adapters between the Waft API and our domain.
 *
 * @since 0.1
 */
public interface IVasttrafikAdapter {
    /**
      * This functions gives you a list of stops related to the search string you provide.
      *
      * @param searchString the query to search for.
      * @return a list of stops if the search was successful. If the search was unsuccessful, an
      * empty list is returned.
      */
     List<IStop> searchForStops(String searchString) throws ConnectionException;

    /**
     * This function gives you a list of the 20 next vehicles headed to a certain stop at the
     * current time
     *
     * @param stop the stop to get arrivals for.
     * @return a list of vehicles headed to the specified stop, if the search was successful. If the
     * search was unsuccessful, an empty list is returned.
     */
     List<IArrivingVehicle> getArrivalsForStop(IStop stop) throws ConnectionException;
}
