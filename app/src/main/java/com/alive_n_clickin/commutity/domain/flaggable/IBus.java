package com.alive_n_clickin.commutity.domain.flaggable;

import com.alive_n_clickin.commutity.domain.flag.IFlag;

import java.util.List;

/**
 * An interface for modelling a bus. A bus should be immutable. All methods that returns
 * mutable objects should return copies of said object, to ensure immutability.
 */
public interface IBus {
    /**
     * @return the Device Gateway id of this bus. This ID is used by ElectriCity to identify the bus.
     */
    String getDGW();

    /**
     * @return the Vehicle Identification Number of this bus. This ID is used by Västtrafik to
     * identify the bus.
     */
    String getVIN();

    /**
     * @return the license number of this bus. This is the ID that's on the license plate of the bus.
     */
    String getLicenseNumber();

    /**
     * @return the BSSID for the WiFi connection on board the bus.
     */
    String getWifiBSSID();

    /**
     * @return a list of all flags on this bus. This method should never return null. If the bus
     * doesn't have anyg flags, it should return an empty list.
     */
    List<IFlag> getFlags();
}
