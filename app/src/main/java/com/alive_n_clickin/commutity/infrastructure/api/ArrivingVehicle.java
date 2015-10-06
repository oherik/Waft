package com.alive_n_clickin.commutity.infrastructure.api;

import com.alive_n_clickin.commutity.domain.Bus;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.NonNull;

/** Consists of a vehicle and when it will arrive to a certain stop
 */
public class ArrivingVehicle {
    private Date arrivalTime;
    @Getter private Bus bus;


    /**
     * Constructor
     * @param bus
     * @param arrivalTime
     */
    public ArrivingVehicle(@NonNull Bus bus, @NonNull Date arrivalTime){
        this.bus = bus;
        this.arrivalTime = arrivalTime;
    }

    /**
     * This method provides the differences between the arrival time and current time.
     * @return
     */
    public Calendar getTimeToArrival() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(this.arrivalTime.getTime() - new Date().getTime()));
        return calendar;
    }

}
