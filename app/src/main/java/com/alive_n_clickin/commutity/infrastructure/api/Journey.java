package com.alive_n_clickin.commutity.infrastructure.api;

import lombok.Getter;

/**
 * @author hjorthjort
 *         Created 08/10/15
 */
public class Journey {
    @Getter private String destination;
    @Getter private String journeyId;

    public Journey(String destination, String journeyId) {
        this.destination = destination;
        this.journeyId = journeyId;
    }
}
