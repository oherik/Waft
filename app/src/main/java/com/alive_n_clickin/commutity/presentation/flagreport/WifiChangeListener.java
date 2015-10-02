package com.alive_n_clickin.commutity.presentation.flagreport;

/**
 * @author hjorthjort
 *         Created 26/09/15
 * A listener that can be implemented to listen to events from the
 * {@link com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver}
 * @since 0.1
 */
public interface WifiChangeListener {

    void onWifiChanged();

}
