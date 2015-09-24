package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by OscarEvertsson on 24/09/15.
 */
public class GpsState implements LocationListener{
    private static GpsState instance;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private Location location;
    private LocationManager locationManager;
    private long MIN_TIME_BW_UPDATES_IN_MILLSECONDS = 1000*60;
    private long MIN_DISTANCE_CHANGE_FOR_UPDATE_IN_METERS = 10;

    private GpsState() {
    }


    public static GpsState getInstance() {
        if (instance == null) {
            instance = new GpsState();
        }
        return instance;
    }

    public Location getLocation(Context context){
        if (locationManager == null){
            try {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isNetworkEnabled && !isGpsEnabled) {
            //Cant do shit
        } else {
            if(isNetworkEnabled) {
                try{
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES_IN_MILLSECONDS,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE_IN_METERS,
                            this);

                    this.location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }

            if(isGpsEnabled) {
                if(location == null) {
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES_IN_MILLSECONDS,
                                MIN_DISTANCE_CHANGE_FOR_UPDATE_IN_METERS,
                                this);

                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return location;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
