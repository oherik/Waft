package com.alive_n_clickin.commutity.infrastructure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

/**
 *
 * @since 0.1
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
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isNetworkEnabled && !isGpsEnabled) {
            showNetworkAndGpsDisabledMessage(context);
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

    private void showNetworkAndGpsDisabledMessage(final Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Can't receive coordinates");
        alertDialog.setMessage("Please turn on your internet connection and gps position");
        alertDialog.setButton(1, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(i);
            }
        });
        alertDialog.setButton(0, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }
}
