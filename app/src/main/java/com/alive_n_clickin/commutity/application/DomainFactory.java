package com.alive_n_clickin.commutity.application;


import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.infrastructure.api.response.Arrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.Stop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.NonNull;

/**
 * This is a factory which responsibility is to create new domain level objects based on the ones
 * supplied by the API requests.
 */
public class DomainFactory{
    /**
     * Creates a new arriving vehicle object based on an API response
     * @param arrival The API response
     * @return  A new arriving vehicle based on the response
     * @throws NullPointerException if the parameter is null
     */
    public static IArrivingVehicle getArrivingVehicle(@NonNull Arrival arrival) {
        //TODO add method to retrieve flags
        List<IFlag> flags = new ArrayList<>();
        flags.add(new Flag(Flag.Type.NO_PRAMS, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        flags.add(new Flag(Flag.Type.OVERCROWDED, "", new Date()));
        flags.add(new Flag(Flag.Type.NO_PRAMS, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        flags.add(new Flag(Flag.Type.MESSY, "", new Date()));
        return new ArrivingVehicle(arrival.getDirection(), arrival.getSname(),
                arrival.getJourneyid(), arrival.getRealArrival(), flags);
    }

    /**
     * Creates a new domain stop object based on an API response
     * @param stop The API response
     * @return A new domain stop object based on the response
     * @throws NullPointerException if the parameter is null
     */
    public static IStop getStop(@NonNull Stop stop){
        return new com.alive_n_clickin.commutity.domain.Stop(stop.getName(), stop.getId());
    }
}
