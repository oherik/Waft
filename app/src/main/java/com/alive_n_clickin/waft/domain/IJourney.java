package com.alive_n_clickin.waft.domain;

/**
 * An interface for journeys. A journey should be able to provide a final destination and a journey
 * ID.
 *
 * @since 1.0
 */
public interface IJourney {
    /**
     * @return the final destination of this journey.
     */
    String getDestination();

    /**
     * @return the unique identifier for this journey.
     */
    String getJourneyId();
}
