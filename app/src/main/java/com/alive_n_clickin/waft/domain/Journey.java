package com.alive_n_clickin.waft.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A simple journey that has a destination and a journey id.
 *
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
public class Journey implements IJourney {
    @Getter private String destination;
    @Getter private String journeyId;

    /**
     * Instantiates a new journey.
     *
     * @param destination the final destination of the journey.
     * @param journeyId the unique identifier for the journey.
     * @throws NullPointerException if any of the parameters are null.
     */
    public Journey(@NonNull String destination, @NonNull String journeyId) {
        this.destination = destination;
        this.journeyId = journeyId;
    }
}
