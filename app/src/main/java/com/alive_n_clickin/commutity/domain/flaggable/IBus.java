package com.alive_n_clickin.commutity.domain.flaggable;

/**
 * An interface for modelling a bus. This interface extends the IFlaggable interface, since
 * all buses should be flaggable.
 */
public interface IBus extends IFlaggable {
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
}
