package com.alive_n_clickin.waft.application;

import android.app.Application;

import com.alive_n_clickin.waft.infrastructure.WifiBroadcastReceiver;

/**
 * This is the "main" class for the application. This class is responsible for keeping track of
 * everything that should be accessible application wide, and for creating instances of all
 * broadcast receivers and services that always should be active and can't be declared in the
 * manifest for whatever reason.
 *
 * Since this class extends the Android Application class, exactly one instance of this class
 * always exists within the application.
 *
 * @since 0.2
 */
public class CentralApplication extends Application {
    private WifiBroadcastReceiver wifiBroadcastReceiver;
    private com.alive_n_clickin.waft.application.IManager manager;

    /**
     * {@inheritDoc}<br><br>
     *
     * This method creates a new WifiBroadcastReceiver, a NearbyBusScanner and a Manager
     * and wires them all together. The WifiBroadcastReceiver and the Manager can be accessed
     * via the provided get methods.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        this.wifiBroadcastReceiver = new WifiBroadcastReceiver(this);
        com.alive_n_clickin.waft.application.NearbyBusScanner nearbyBusScanner = new com.alive_n_clickin.waft.application.NearbyBusScanner(wifiBroadcastReceiver, this.getApplicationContext());
        this.manager = new com.alive_n_clickin.waft.application.Manager(nearbyBusScanner);
    }

    /**
     * @return the Manager instance that is used throughout the application.
     */
    public com.alive_n_clickin.waft.application.IManager getManager() {
        return this.manager;
    }

    /**
     * @return the WifiBroadcastReceiver that is used throughout the application.
     */
    public WifiBroadcastReceiver getWifiBroadcastReceiver() {
        return this.wifiBroadcastReceiver;
    }
}
