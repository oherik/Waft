package com.alive_n_clickin.waft.infrastructure;

import com.alive_n_clickin.waft.util.event.IEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * An event for when the list of nearby wifi networks changes. The event contains information about
 * all networks that are currently in range.
 *
 * @since 0.2
 */
public class NewWifiScanAvailableEvent implements IEvent {
    private List<String> BSSIDs;

    /**
     * Initiates a new NewWifiScanAvailableEvent.
     *
     * @param BSSIDs a list of the BSSID:s of all networks that are currently in range.
     */
    public NewWifiScanAvailableEvent(List<String> BSSIDs) {
        this.BSSIDs = new ArrayList<>(BSSIDs);
    }

    /**
     * @return the BSSID:s of all networks that are currently in range.
     */
    public List<String> getBSSIDs() {
        return new ArrayList<>(this.BSSIDs);
    }
}
