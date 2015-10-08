package com.alive_n_clickin.commutity.application;


import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.infrastructure.api.response.Stop;

import lombok.NonNull;

/**
 * This is a factory which responsibility is to create new domain level objects based on the ones
 * supplied by the API requests.
 */
public class StopFactory {

    /**
     * Creates a new domain stop object based on an API response.
     *
     * @param stop The API response
     * @return A new domain stop object based on the response
     * @throws NullPointerException if the parameter is null
     */
    public static IStop getStop(@NonNull Stop stop){
        return new com.alive_n_clickin.commutity.domain.Stop(stop.getName(), stop.getId());
    }
}
