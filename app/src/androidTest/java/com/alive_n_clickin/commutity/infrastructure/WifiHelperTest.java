package com.alive_n_clickin.commutity.infrastructure;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.test.AndroidTestCase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hjorthjort
 *         Created 29/09/15
 * @since 0.1
 */
public class WifiHelperTest extends AndroidTestCase {

    //Check that at least SOME scans, possibly made at two different times, have some common results.
    //Threading and state changes can make this test fail even though the method might work (if scan results
    //are updated while the method is run). But this should check against severe breakage.
    public void testScanning() {
        WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> lastScans = wifiManager.getScanResults();
        List<String> scansToTest = new WifiHelper(getContext()).getNearbyBSSIDs();

        Set<String> bssids = new HashSet<>();

        for (ScanResult result :
                lastScans) {
            bssids.add(result.BSSID);
        }

        bssids.retainAll(scansToTest);

        assertFalse("No common bssids", bssids.isEmpty());

    }
}
