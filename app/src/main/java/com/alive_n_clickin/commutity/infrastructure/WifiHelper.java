package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A helper class for talking to the Android WifiManager.
 *
 * @since 0.2
 */
public class WifiHelper {
    private WifiManager wifiManager;

    /**
     * Initiates a new WifiHelper with the given context. The context is needed when talking to
     * Androids WifiManager.
     *
     * @param context the context to initiate the WifiHelper from.
     */
    public WifiHelper(Context context) {
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Checks whether or not the wifi is enabled.
     *
     * @return true if wifi is enabled or false when disabled.
     */
    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * Initiates a new wifi scan. When the scan is finished, a "android.net.wifi.SCAN_RESULTS"
     * broadcast will be sent by the Android system.
     */
    public void initiateWifiScan() {
        wifiManager.startScan();
    }

    /**
     * @return the BSSID of the network the device is currently connected to.
     */
    public String getBSSIDOfCurrentNetwork() {
        return wifiManager.getConnectionInfo().getBSSID();
    }

    /**
     * @return a list with BSSID:s of all nearby networks, sorted by signal strength
     */
    public List<String> getNearbyBSSIDs() {
        List<ScanResult> scanResults = wifiManager.getScanResults();

        // Sort list according to signal level, strongest first.
        Collections.sort(scanResults, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult lhs, ScanResult rhs) {
                return rhs.level - lhs.level;
            }
        });

        // Fetch all BSSID:s from the scan results
        List<String> BSSIDs = new ArrayList<>();
        for (ScanResult scanResult : scanResults) {
            BSSIDs.add(scanResult.BSSID);
        }

        return BSSIDs;
    }

    /**
     * Enables the devices wifi connection, if it isn't already enabled.
     */
    public void enableWifi() {
        wifiManager.setWifiEnabled(true);
    }
}
