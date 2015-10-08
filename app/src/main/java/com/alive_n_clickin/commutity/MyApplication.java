package com.alive_n_clickin.commutity;

import android.app.Application;

import com.alive_n_clickin.commutity.application.BusManager;
import com.alive_n_clickin.commutity.application.IBusManager;
import com.alive_n_clickin.commutity.application.NearbyBusScanner;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;

/**
 * This is the "main" class for the application. This class is responsible for keeping track of
 * everything that should be accessible application wide, and for creating instances of all
 * broadcast receivers and services that always should be active and can't be declared in the
 * manifest for whatever reason.
 *
 * Since this class extends the Android Application class, exactly one instance of this class
 * always exists within the application.
 */
public class MyApplication extends Application {
    private WifiBroadcastReceiver wifiBroadcastReceiver;
    private IBusManager busManager;

    /**
     * {@inheritDoc}<br><br>
     *
     * This method creates a new WifiBroadcastReceiver, a NearbyBusScanner and a BusManager
     * and wires them all together. The WifiBroadcastReceiver and the BusManager can be accessed
     * via the provided get methods.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        this.wifiBroadcastReceiver = new WifiBroadcastReceiver(this);
        NearbyBusScanner nearbyBusScanner = new NearbyBusScanner(wifiBroadcastReceiver);
        this.busManager = new BusManager(nearbyBusScanner);
    }

    /**
     * @return the BusManager instance that is used throughout the application.
     */
    public IBusManager getBusManager() {
        return this.busManager;
    }

    /**
     * @return the WifiBroadcastReceiver that is used throughout the application.
     */
    public WifiBroadcastReceiver getWifiBroadcastReceiver() {
        return this.wifiBroadcastReceiver;
    }
}
