package com.alive_n_clickin.commutity.domain;

import java.util.List;

/**
 * An interface for vehicles arriving to a stop. An arriving vehicle should be able to provide time
 * until arrival, and the flags that the vehicle is flagged with.
 */
public interface IArrivingVehicle extends IVehicle {
    /**
     * @return time until arrival in milliseconds. This should be the real time until the vehicle
     * arrives, and not the time until the scheduled time of arrival.
     */
    long getTimeToArrival();

    /**
     * @return a list of flags that this vehicle is flagged with.
     */
    List<IFlag> getFlags();
}
