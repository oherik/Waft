package com.alive_n_clickin.commutity.event;

import com.alive_n_clickin.commutity.util.event.IEvent;

import java.util.ArrayList;
import java.util.List;

// TODO: Documentation
public class WifiBSSIDChangeEvent implements IEvent {
    private List<String> BSSIDs;

    // TODO: Documentation
    public WifiBSSIDChangeEvent(List<String> BSSIDs) {
        this.BSSIDs = new ArrayList<>(BSSIDs);
    }

    // TODO: Documentation
    public List<String> getBSSIDs() {
        return new ArrayList<>(this.BSSIDs);
    }
}
