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
                "00:13:95:13:4b:be",
                "00:13:95:14:3b:f0",
                "00:13:95:14:69:8a",
                "00:13:95:13:49:f7",
                "00:13:95:0f:92:a4",
                "00:13:95:13:62:96",
                "00:13:95:13:4b:bc",
                "00:13:95:14:3b:f2",
                "00:13:95:13:5f:20"
        };

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
     * Get the best guess for which bus you are one depending on value of wifi connection
     * @param context
     * @return
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
