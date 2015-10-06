package com.alive_n_clickin.commutity.event;

import com.alive_n_clickin.commutity.util.event.IEvent;

// TODO: Documentation
public class WifiStateChangeEvent implements IEvent {

    private boolean wifiEnabled;

    // TODO: Documentation
    public WifiStateChangeEvent(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    // TODO: Documentation
    public boolean isWifiEnabled() {
        return this.wifiEnabled;
    }
}
