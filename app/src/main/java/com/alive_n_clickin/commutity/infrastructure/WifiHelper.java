package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author hjorthjort
 *         Created 24/09/15
 *
 * Singleton tool for getting wifi information
 */
public class WifiHelper {
    private WifiManager wifiManager;

    public WifiHelper(Context context) {
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Checks whether or not the wifi is enabled.
     * @return true if wifi is enabled or false when disabled.
     */
    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * Initate a scan for wifis to make them up to date
     */
    public void initiateWifiScan() {
        wifiManager.startScan();
    }

    /**
     * Get information on the WiFi the device is connected to.
     * @return
     */
    public String getBSSIDOfCurrentNetwork() {
        return wifiManager.getConnectionInfo().getBSSID();
    }

    /**
     * Get objects representing all nearby wifis, with MAC Address (BSSID), Name (SSID) and more. See
     * {@link ScanResult} for all available fields
     * @return list with all results, sorted by signal strength
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
     * Enables the devices WifiConnection, if it isn't already enabled
     */
    public void enableWifi() {
        wifiManager.setWifiEnabled(true);
    }
}
