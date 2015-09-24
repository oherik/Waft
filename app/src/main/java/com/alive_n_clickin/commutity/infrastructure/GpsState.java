package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by OscarEvertsson on 24/09/15.
 */
public class GpsState {
    private static GpsState instance = null;
    private Location location;
    private LocationManager locationManager = null;

    private GpsState() {
    }


    public static GpsState getInstance() {
        if (instance == null) {
            instance = new GpsState();
        }
        return instance;
    }

    /**
     * Fetches the last location and returns it.
     * @return current location.
     */
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
