package com.alive_n_clickin.commutity.infrastructure.api.response;

import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import lombok.Getter;
import lombok.ToString;

/**
 * Consists the data of a vehicle and it's arrival as per the Vasttrafik API. To ease further use
 * of this class it has built in methods for converting the arrival date and time to a Java Date
 * object, as well as calculating the time to arrival.
 */
@ToString
public class JsonArrival {
    /**
     * Scheduled arrival time
     */
    @Getter private String time;
    /**
     * The real arrival time, including delays
     */
    @Getter private String rtTime;
    @Getter private String date;
    @Getter private String journeyid;
    @Getter private String direction;
    @Getter private String name;
    /**
     * The line number (short name)
     */
    @Getter private String sname;

    @Getter private DateFormat dateFormatter;

    /**
     * Constructor, initializes the date formatter
     */
    public JsonArrival() {
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
    }

    /**
     * Creates a new Java Date for when the vehicle is scheduled to arrive, based on the information
     * given from the API call
     * @return  The date and time the vehicle is scheduled to arrive
     */
    public Date getScheduledArrival() {
        Date arrival = new Date();
        try {
            arrival = dateFormatter.parse(date + " " + time);
        } catch (ParseException e) {
            Log.e(LogUtils.getLogTag(this), e.getStackTrace().toString());
        }
        return arrival;
    }

    /**
     * Creates a new Java Date for when the vehicle will arrive, including delays if present,
     * based on the information given from the API call
     * @return  The date and time the vehicle will arrive
     */
    public Date getRealArrival() {
        Date arrival = new Date();
        try {
            arrival = dateFormatter.parse(date + " " + rtTime);
        } catch (ParseException e) {
            Log.e(LogUtils.getLogTag(this), e.getStackTrace().toString());
        }
        return arrival;
    }

    /**
     * This method provides the differences between the scheduled arrival time and current time.
     * @return the difference in time units (milliseconds)
     */
    public Long getScheduledTimeToArrival() {
        return getScheduledArrival().getTime() - System.currentTimeMillis();
    }

}
