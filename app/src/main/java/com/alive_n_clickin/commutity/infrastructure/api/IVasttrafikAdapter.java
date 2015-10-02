package com.alive_n_clickin.commutity.infrastructure.api;

import java.util.List;

/**
 * This interface represent the possible request to the Vasttrafik API combined with their responses.
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
}
