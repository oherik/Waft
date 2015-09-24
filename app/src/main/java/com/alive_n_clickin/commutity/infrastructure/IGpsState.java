package com.alive_n_clickin.commutity.infrastructure;


import android.location.Location;

/**
 * This interface models a current gps location.
 */
public interface IGpsState {

    /**
     * Fetches the newest location and returns it.
     * @return current location.
     */
     Location getCurrentLocation();
}
