package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.infrastructure.api.response.Arrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.Stop;

import java.util.List;

/**
 * This interface represent the possible request to the Vasttrafik API combined with their responses.
 * @since 0.1
 */
public interface IVasttrafikAdapter {

     /**
      * This function gives you a list of the nearest stops to the location provided.
      * @param longitude
      * @param latitude
      * @return a list of stops if the search was successful or null if failed.
      */
     List<Stop> getNearbyStations(double longitude,double latitude);

     /**
      * This functions gives you a list of stops related to the search string you provide.
      * @param searchString
      * @returns a list of stops if the search was successful.
      * Otherwise returns null since there was no result.
      */
     List<Stop> getSearchStops(String searchString);

    /**
     * This function gives you a list of the 20 next vehicles headed to a certain stop at the current time
     * @param stop
     * @return a list of vehicles headed to the specified stop, it the search was successful.
     * Returns null if the search was unsuccessful.
     */
     List<Arrival> getVehiclesHeadedToStop(Stop stop);
}
