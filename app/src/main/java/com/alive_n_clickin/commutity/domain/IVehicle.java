package com.alive_n_clickin.commutity.domain;

import java.util.List;

/**
 * An interface for vehicles. A vehicle should be able to provide a destination, a short name for
 * it's route and the id of it's current journey.
 *
 * @since 0.2
 */
public interface IVehicle {
    /**
     * @return the final destination of the journey this vehicle is on. For example "Sahlgrenska".
     */
    String getDestination();

    /**
     * @return the short name for the vehicle's route. Usually simply a line number (e.g. "55"),
     * but can also be longer, e.g. "Grön Express".
     */
    String getShortRouteName();

    /**
     * @return a unique ID for the journey the vehicle is on. The journey ID changes every time the
     * vehicle gets to the end stop. This ID is used by Västtrafik to identify journeys.
     *
     */
    String getJourneyID();

    /**
     * @return a list of flags that this vehicle is flagged with.
     */
    List<IFlag> getFlags();
}
