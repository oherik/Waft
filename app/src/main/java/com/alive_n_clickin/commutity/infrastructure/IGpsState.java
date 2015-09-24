package com.alive_n_clickin.commutity.infrastructure;


import android.content.Context;
import android.location.Location;

/**
 * This interface models a current gps location.
 */
public interface IGpsState {

    /**
     * Fetches the last location and returns it.
     * @return current location.
     */
    Location getLastLocation(Context context);

    IGpsState getInstance();
}
