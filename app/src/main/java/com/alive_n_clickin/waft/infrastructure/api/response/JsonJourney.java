package com.alive_n_clickin.waft.infrastructure.api.response;

import lombok.Getter;

/**
 * @since 0.2
 */
public class JsonJourney {
    @Getter private String destination;
    @Getter private String journeyId;

    public JsonJourney(String destination, String journeyId) {
        this.destination = destination;
        this.journeyId = journeyId;
    }
}
