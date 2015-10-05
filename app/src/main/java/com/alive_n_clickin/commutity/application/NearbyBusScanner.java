package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.event.NewBusNearbyEvent;
import com.alive_n_clickin.commutity.event.WifiBSSIDChangeEvent;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObservable;
import com.alive_n_clickin.commutity.util.event.IObservableHelper;
import com.alive_n_clickin.commutity.util.event.IObserver;
import com.alive_n_clickin.commutity.util.event.ObservableHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class listens to WifiBSSIDChangeEvents and checks if any nearby BSSID belongs to a known bus.
 * If it finds a match, it sends a NewBusNearbyEvent with the DGW of the found bus. If no match is
 * found, it sends a NewBusNearbyEvent with the DGW parameter set to null.
 */
public class NearbyBusScanner implements IObserver, IObservable {
    private IObservableHelper observableHelper = new ObservableHelper();

    private static Map<String, String> buses = new HashMap<>();

    /**
     * Initiates a NearbyBusScanner and registers it as an observer to the specified WifiBroadcastReceiver.
     *
     * @param wifiBroadcastReceiver the WifiBroadcastReceiver to listen to for WifiBSSIDChangeEvents.
     */
    public NearbyBusScanner(WifiBroadcastReceiver wifiBroadcastReceiver) {
        wifiBroadcastReceiver.addObserver(this);

        // TODO: Add all buses
        buses.put("04:f0:21:10:09:df", "Ericsson$100021"); // EPO 136
        buses.put("04:f0:21:10:09:b9", "Ericsson$171164"); // EOG 627
        buses.put("04:f0:21:10:09:e8", "Ericsson$100022"); // EPO 143
        buses.put("04:f0:21:10:09:b7", "Ericsson$171330"); // EOG 634
        buses.put("04:f0:21:10:09:53", "Ericsson$171327"); // EOG 622
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * This implementation of onEvent gets the BSSID:s from the WifiBSSIDChangeEvent and checks if
     * the BSSID of any known bus is in the list. If the list contains a known bus, a NewBusNearbyEvent
     * is sent to all observers with the DGW of the bus. If the list doesn't contain a known bus,
     * a NewBusNearbyEvent is sent to all observers with the DGW parameter set to null.
     *
     * @param event
     */
    @Override
    public void onEvent(IEvent event) {
        if (event instanceof WifiBSSIDChangeEvent) {
            handleWifiBSSIDChangeEvent((WifiBSSIDChangeEvent) event);
        }
    }

    private void handleWifiBSSIDChangeEvent(WifiBSSIDChangeEvent event) {
        List<String> BSSIDs = event.getBSSIDs();
        for (String BSSID : BSSIDs) {
            if (buses.containsKey(BSSID)) {
                String DGW = buses.get(BSSID);
                observableHelper.notifyObservers(new NewBusNearbyEvent(DGW));
                break;
            }
        }

        // if no bus is in range, send a new event with DGW null
        observableHelper.notifyObservers(new NewBusNearbyEvent(null));
    }

    @Override
    public void addObserver(IObserver observer) {
        observableHelper.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observableHelper.removeObserver(observer);
    }
}
