package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjorthjort
 *         Created 24/09/15
 *
 * Class for finding nearby vehicles based on wifi position.
 * @since 0.1
 */
public class NearbyVehiclesScanner {

    private final static Map<String, String> VEHICHLE_ADDRESSES = new HashMap<>();
    private static NearbyVehiclesScanner instance;

    private NearbyVehiclesScanner() {
        //All the vehicles in order
        String[] vehicles = {
                "Ericsson$100020",
                "Ericsson$100021",
                "Ericsson$100022",
                "Ericsson$171164",
                "Ericsson$171234",
                "Ericsson$171235",
                "Ericsson$171327",
                "Ericsson$171328",
                "Ericsson$171329",
                "Ericsson$171330",
        };
        //All the addresses in order
        String[] macAddresses = {
                "00:13:95:13:49:f5",
                "04:f0:21:10:09:df",
                "04:f0:21:10:09:e8",
                "04:f0:21:10:09:b8",
                "00:13:95:13:49:f7",
                "04:f0:21:10:09:5b",
                "04:f0:21:10:09:53",
                "04:f0:21:10:09:b9",
                "00:13:95:14:3b:f2",
                "04:f0:21:10:09:b7"
        };

        //Associate the addresses with busses, using the addresses as keys
        for (int i = 0; i < vehicles.length; i++) {
            VEHICHLE_ADDRESSES.put(macAddresses[i], vehicles[i]);
        }
    }

    public static synchronized NearbyVehiclesScanner getInstance() {
        if (instance == null) {
            instance = new NearbyVehiclesScanner();
        }
        return instance;
    }

    /**
     * Get the best guess for which bus you are one depending on value of wifi connection. The best candidate
     * is selected by
     *      first: checking if the phone is connected to a bus wifi
     *      second: checking which of the buses, if any, has the strongest wifi nearby
     * If there is a match on the first, then that bus is returned. If there is a match on the second,
     * the one with the strongest wifi signal is returned.
     *
     * If no bus is found, null is returned.
     *
     * @param context The context for which the WiFi state will be checked to fins busses
     * @return the dgw of the best candidate for closest bus, or null if none is found.
     */
    public String getBestGuess(Context context) {
        WifiHelper wifiHelper = WifiHelper.getInstance();
        WifiInfo wifiInfo = wifiHelper.getCurrentWifiConnection(context);
        String bssid = wifiInfo.getBSSID();
        if (bssid != null && !bssid.equals("00:00:00:00:00:00")) {
            if (VEHICHLE_ADDRESSES.containsKey(bssid)) {
                return VEHICHLE_ADDRESSES.get(wifiInfo.getBSSID());
            }
        }

        List<ScanResult> scanResults = wifiHelper.getNearbyMacAddresses(context);
        for (ScanResult result :
                scanResults) {
            if (VEHICHLE_ADDRESSES.containsKey(result.BSSID)) {
                return VEHICHLE_ADDRESSES.get(result.BSSID);
            }
        }

        return null;
    }
}
