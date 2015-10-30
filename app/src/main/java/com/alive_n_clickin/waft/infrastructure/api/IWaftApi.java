package com.alive_n_clickin.waft.infrastructure.api;

import com.alive_n_clickin.waft.infrastructure.api.response.JsonFlag;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;

/**
 * An interface for Waft's API.
 *
 * @since 1.0
 */
public interface IWaftApi {
    /**
     * Returns a list of all flags for a specific journey.
     *
     * @param journeyId the journey to get flags for.
     * @return a list of all flags for a specific journey. If there are no flags for the journey,
     * an empty list is returned. If anything goes wrong with the request, null is returned.
     */
    List<JsonFlag> getFlagsForJourney(String journeyId) throws SocketTimeoutException;

    /**
     * Adds a flag to the Waft database with the specified info.
     *
     * @param dgw the DGW for the bus to flag.
     * @param journeyId the journey id the bus is on currently.
     * @param flagTypeId the id of the flag type.
     * @param comment a comment for the flag.
     * @param createdTime the time the flag was created.
     * @return true if the bus was successfully flagged, otherwise false.
     */
    boolean addFlag(String dgw, String journeyId, int flagTypeId, String comment, Date createdTime);

    /**
     * Removes a flag from the Waft database.
     *
     * @param id the id of the flag to remove.
     * @return true if the removal was successful, otherwise false.
     */
    boolean deleteFlag(String id);
}
