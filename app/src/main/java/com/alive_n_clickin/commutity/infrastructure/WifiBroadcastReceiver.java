package com.alive_n_clickin.commutity.infrastructure;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

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
public class WifiBroadcastReceiver extends BroadcastReceiver implements IObservable<WifiBSSIDChangeEvent> {
    private IObservableHelper<WifiBSSIDChangeEvent> observableHelper = new ObservableHelper<>();

    /**
     * Initiates a WifiBroadcastReceiver and registers it as a receiver in the application for
     * intents of the type "android.net.wifi.SCAN_RESULTS".
     *
     * @param application the application to register with.
     */
    public WifiBroadcastReceiver(Application application) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        application.registerReceiver(this, intentFilter);
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * This implementation of onReceive scans for all nearby BSSID:s and notifies it's observers
     * with a new WifiBSSIDChangeEvent with all the BSSID:s.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        WifiHelper wifiHelper = new WifiHelper(context);
        List<String> nearbyBSSIDs = wifiHelper.getNearbyBSSIDs();
        observableHelper.notifyObservers(new WifiBSSIDChangeEvent(nearbyBSSIDs));
    }

    @Override
    public void addObserver(IObserver<WifiBSSIDChangeEvent> observer) {
        observableHelper.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver<WifiBSSIDChangeEvent> observer) {
        observableHelper.removeObserver(observer);
    }
}
