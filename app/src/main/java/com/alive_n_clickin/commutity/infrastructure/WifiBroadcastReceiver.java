package com.alive_n_clickin.commutity.infrastructure;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import com.alive_n_clickin.commutity.event.NewWifiScanAvailableEvent;
import com.alive_n_clickin.commutity.event.WifiStateChangeEvent;
import com.alive_n_clickin.commutity.util.event.IObservable;
import com.alive_n_clickin.commutity.util.event.IObservableHelper;
import com.alive_n_clickin.commutity.util.event.IObserver;
import com.alive_n_clickin.commutity.util.event.ObservableHelper;

import java.util.List;

/**
 * This class receives intents from the system regarding the WiFi connection on the device. It then
 * has the task of retrieving relevant information about the connection and forward it to other
 * modules that has an interest in the data.
 */
public class WifiBroadcastReceiver extends BroadcastReceiver implements IObservable {
    private IObservableHelper observableHelper = new ObservableHelper();

    /**
     * Initiates a WifiBroadcastReceiver and registers it as a receiver in the application for
     * intents of the type "android.net.wifi.SCAN_RESULTS".
     *
     * @param application the application to register with.
     */
    public WifiBroadcastReceiver(Application application) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        application.registerReceiver(this, intentFilter);
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * This implementation of onReceive scans for all nearby BSSID:s and notifies it's observers
     * with a new NewWifiScanAvailableEvent with all the BSSID:s.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        WifiHelper wifiHelper = new WifiHelper(context);
        if (action.equals("android.net.wifi.SCAN_RESULTS")) {
            List<String> nearbyBSSIDs = wifiHelper.getNearbyBSSIDs();
            observableHelper.notifyObservers(new NewWifiScanAvailableEvent(nearbyBSSIDs));
        } else if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            int wifiState = intent.getIntExtra("wifi_state", -1);

            if (wifiState == WifiManager.WIFI_STATE_ENABLED
                    || wifiState == WifiManager.WIFI_STATE_DISABLED) {
                boolean wifiEnabled = wifiHelper.isWifiEnabled();
                observableHelper.notifyObservers(new WifiStateChangeEvent(wifiEnabled));
            }
        }
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
