package com.alive_n_clickin.commutity.infrastructure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alive_n_clickin.commutity.presentation.flagreport.WifiChangeListener;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 *         Created 26/09/15
 *
 * Class for delegating new about changed wifi states to all interested listeners
 * @since 0.1
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

    private static final List<WifiChangeListener> listeners = new LinkedList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        notifyWifiChangeListeners();
    }

    private void notifyWifiChangeListeners() {
        for (WifiChangeListener listener : listeners) {
            listener.onWifiChanged();
        }
    }

    public static void register(WifiChangeListener newListener) {
        listeners.add(newListener);
    }

    public static void unregister(WifiChangeListener listener) {
        listeners.remove(listener);
    }
}
