package com.alive_n_clickin.commutity.domain;

import java.util.Date;

/**
 * An interface for vehicles arriving to a stop. An arriving vehicle should be able to provide time
 * until arrival, and the flags that the vehicle is flagged with.
 *
 * An arriving vehicle should be comparable on time until arrival, where vehicles with less time
 * until arrival should come first.
 *
 * @since 0.2
 */
public interface IArrivingVehicle extends IVehicle, Comparable<IArrivingVehicle> {
    /**
     * @return a date containing the day and time of the real, not scheduled, arrival
     */
    Date getArrivalTime();

    /**
     * Get the current time to arrival
     * @return the current time to arrival, in milliseconds
     */
    long getTimeToArrival();

    int getLineColor();
}
