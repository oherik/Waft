package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

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

    private static WifiHelper instance;

    private WifiHelper() {

    }

    public static synchronized WifiHelper getInstance() {
        if (instance == null) {
            instance = new WifiHelper();
        }
        return instance;
    }

    /**
     * Initate a scan for wifis to make them up to date
     * @param context
     */
    public void scanForWifis(Context context) {
        getWifiManager(context).startScan();
    }

    /**
     * Get information on the WiFi the device is connected to.
     * @param context
     * @return
     */
    public WifiInfo getCurrentWifiConnection(Context context) {
        WifiManager wifiManager = getWifiManager(context);
        return wifiManager.getConnectionInfo();
    }

    /**
     * Get objects representing all nearby wifis, with MAC Address (BSSID), Name (SSID) and more. See
     * {@link ScanResult} for all available fields
     * @param context the context which the requests will be made on
     * @return list with all results, sorted by signal strength
     */
    public List<ScanResult> getNearbyMacAddresses(Context context) {
        scanForWifis(context);
        List<ScanResult> scanResults = getWifiManager(context).getScanResults();

        //Sort list according to signal level, strongest first.
        Collections.sort(scanResults, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult lhs, ScanResult rhs) {
                return rhs.level - lhs.level;
            }
        });
        //Debug: Check that list is correctly sorted
//        for (ScanResult result :
//                scanResults) {
//            Log.d(LogUtils.getLogTag(this), "STRENGTH: "+result.level);
//        }
        return scanResults;
    }

    private WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }
}
