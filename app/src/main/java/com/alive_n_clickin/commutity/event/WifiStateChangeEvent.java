package com.alive_n_clickin.commutity.event;

import com.alive_n_clickin.commutity.util.event.IEvent;

/**
 * An event for when the state of the wifi changes from disabled to enabled, or vice versa.
 * The event contains information about the current state of the wifi.
 */
public class WifiStateChangeEvent implements IEvent {
    private boolean wifiEnabled;

    /**
     * Initiates a new WifiStateChangeEvent.
     *
     * @param wifiEnabled the current state of the wifi. True if enabled, false if disabled.
     */
    public WifiStateChangeEvent(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    /**
     * @return true if wifi is enabled, false if disabled.
     */
    public boolean isWifiEnabled() {
        return this.wifiEnabled;
    }
}
