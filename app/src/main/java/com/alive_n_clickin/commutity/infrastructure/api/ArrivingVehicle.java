package com.alive_n_clickin.commutity.infrastructure.api;

import android.util.Log;

import com.alive_n_clickin.commutity.domain.Bus;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.util.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import lombok.ToString;

/**
 * Consists the data of a vehicle and it's arrival as per the Vasttrafik API. To ease further use
 * of this class it has built in methods for converting the arrival date and time to a Java Date
 * object, as well as calculating the time to arrival.
 */
@ToString
public class ArrivingVehicle{
    private String time;
    private String date;
    private long journeyid;
    private String direction = "Mot lindholmen";
    private String name = "";
    private String routeNumber = "55";

    //TODO Just for testing, change this when we implement another key value for the vehiclesg
    public Bus getBus() {
        List<IFlag> list = new ArrayList<>();
        list.add(new Flag(Flag.Type.OVERCROWDED, "", new Date()));
        return new Bus("", direction, name, routeNumber, list);
    }

    /**
     * Creates a new Java Date for when the vehicle will arrive, based on the information given from
     * the API call
     * @return  The date and time the vehicle will arrive
     */
    public Date getArrival(){
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        Log.e(LogUtils.getLogTag(this), "dddddddddddddd " + date + "    " + time);
        Date arrival = new Date();
        try {
            arrival = dateFormatter.parse(date + " " + time);
        } catch (ParseException e) {
            Log.e(LogUtils.getLogTag(this), e.getStackTrace().toString());
        }
        return arrival;
    }

    /**
     * This method provides the differences between the arrival time and current time.
     * @return a the difference in time units (milliseconds)
     */
    public Long getTimeToArrival() {
        Date arrival = getArrival();

        return arrival.getTime() - System.currentTimeMillis();
    }
}
