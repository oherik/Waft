package com.alive_n_clickin.commutity.infrastructure;

import com.alive_n_clickin.commutity.util.event.IEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 02/10/15.
 */
public class WifiBSSIDChangeEvent implements IEvent {
    private List<String> BSSIDs;

    public WifiBSSIDChangeEvent(List<String> BSSIDs) {
        this.BSSIDs = new ArrayList<>(BSSIDs);
    }

    public List<String> getBSSIDs() {
        return new ArrayList<>(this.BSSIDs);
    }
}
