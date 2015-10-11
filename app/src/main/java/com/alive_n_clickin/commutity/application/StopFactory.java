package com.alive_n_clickin.commutity.application;


import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;

import lombok.NonNull;

/**
 * This is a factory for creating stops. The idea is that you ask this factory
 * to build a stop from existing data (for example an ID or a response object received from
 * an external API), and that the factory simply responds with a stop object. The factory
 * is responsible for fetching all the data required for that stop from whatever sources it needs.
 */
public class StopFactory {

    /**
     * Creates a new domain stop object based on an API response.
     *
     * @param jsonStop The API response
     * @return A new domain stop object based on the response
     * @throws NullPointerException if the parameter is null
     */
    public static IStop getStop(@NonNull JsonStop jsonStop) {
        return new com.alive_n_clickin.commutity.domain.Stop(jsonStop.getName(), jsonStop.getId());
    }
}
