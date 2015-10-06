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

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/** Consists of a vehicle and when it will arrive to a certain stop
 */
@ToString
public class ArrivingVehicle {
    //private Date arrivalTime;
    //private Bus bus;
    private String time;
    private String date;
    private long journeyid;
    private String direction;
    private String name;
    private String sname;


    /**
     * Constructor
   //  * @param bus
    // * @param arrivalTime
     */
  //  public ArrivingVehicle(@NonNull Bus bus, @NonNull Date arrivalTime){
   //     this.bus = bus;
    //    this.arrivalTime = arrivalTime;
   // }

    //TODO Just for testing
    public Bus getBus(){
        return new Bus("", direction, name, sname, new ArrayList<IFlag>());
    }

    /**
     * This method provides the differences between the arrival time and current time.
     * @return
     */
    public Calendar getTimeToArrival() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-DD HH:mm");
        Date arrival = new Date();
        try {
            arrival = dateFormatter.parse(date + " " + time);
        }catch(ParseException e){
            Log.e(LogUtils.getLogTag(this), e.getStackTrace().toString());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(arrival.getTime() - new Date().getTime()));
        return calendar;
    }
}
