package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.event.NewWifiNetworksInRangeEvent;
import com.alive_n_clickin.commutity.event.NewBusNearbyEvent;
import com.alive_n_clickin.commutity.event.WifiStateChangeEvent;
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
        buses.put("04:f0:21:10:09:5b", "Ericsson$171235"); // EOG 616
        buses.put("04:f0:21:10:09:b8", "Ericsson$171164"); // EOG 604

        //Bssids below are purely for testing (and for fun!)
        buses.put("24:a0:74:75:8a:4c", "Ericsson$171164"); //Casa del Lollo
        buses.put("d0:c7:89:33:27:3e", "Ericsson$100021"); //M-salarna
        buses.put("28:c6:8e:71:8a:b8", "Ericsson$171164"); //Casa del Hjort
        buses.put("44:ad:d9:f1:51:6e", "Ericsson$100022"); //Hubben study room
        buses.put("24:de:c6:3b:21:c4", "Ericsson$171330"); //Launchpad
        buses.put("d0:c7:89:33:27:3e", "Ericsson$171327"); //EDUROAM (which one?)
        buses.put("88:1d:fc:41:92:90", "Ericsson$171235"); //EDUROAM (which one?)
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * This implementation of onEvent gets the BSSID:s from the NewWifiNetworksInRangeEvent and checks if
     * the BSSID of any known bus is in the list. If the list contains a known bus, a NewBusNearbyEvent
     * is sent to all observers with the DGW of the bus. If the list doesn't contain a known bus,
     * a NewBusNearbyEvent is sent to all observers with the DGW parameter set to null.
     *
     * @param event
     */
    @Override
    public void onEvent(IEvent event) {
        if (event instanceof NewWifiNetworksInRangeEvent) {
            handleWifiBSSIDChangeEvent((NewWifiNetworksInRangeEvent) event);
        } else if (event instanceof WifiStateChangeEvent) {
            handleWifiStateChangeEvent((WifiStateChangeEvent) event);
        }
    }

    private void handleWifiBSSIDChangeEvent(NewWifiNetworksInRangeEvent event) {
        String DGW = null;

        List<String> BSSIDs = event.getBSSIDs();
        for (String BSSID : BSSIDs) {
            if (buses.containsKey(BSSID)) {
                DGW = buses.get(BSSID);
                break;
            }
        }

        observableHelper.notifyObservers(new NewBusNearbyEvent(DGW));
    }

    private void handleWifiStateChangeEvent(WifiStateChangeEvent event) {
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
