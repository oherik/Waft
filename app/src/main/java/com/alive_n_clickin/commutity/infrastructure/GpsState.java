package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by OscarEvertsson on 24/09/15.
 */
public class GpsState implements IGpsState {
    private IGpsState instance = null;
    private Location location;
    private LocationManager locationManager = null;

    private GpsState() {
    }

    public IGpsState getInstance() {
        if (this.instance == null) {
            this.instance = new GpsState();
        }
        return this.instance;
    }

    @Override
    public Location getLastLocation(Context context) {
        try {
            if (this.locationManager == null){
                this.locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            }
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch(SecurityException e){
            e.printStackTrace();
        }
        return this.location;
    }
}
