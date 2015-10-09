package com.alive_n_clickin.commutity.application;

import android.os.AsyncTask;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.event.NewBusNearbyEvent;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IWaftAdapter;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObservableHelper;
import com.alive_n_clickin.commutity.util.event.IObserver;
import com.alive_n_clickin.commutity.util.event.ObservableHelper;

/**
 * An implementation of the IBusManager interface. This implementation listens to a NearbyBusScanner
 * for events regarding nearby buses to keep track of which bus the user is currently on.
 */
public class BusManager implements IBusManager, IObserver {
    private IObservableHelper observableHelper = new ObservableHelper();

    private IBus currentBus = null;

    /**
     * Initiates a new BusManager that listens to the supplied NearbyBusScanner.
     *
     * @param nearbyBusScanner the NearbyBusScanner that this BusManager should listen to for
     *                         events regarding nearby buses.
     */
    public BusManager(NearbyBusScanner nearbyBusScanner) {
        nearbyBusScanner.addObserver(this);
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * Sends a post request to our backend to flag the current bus with the supplied flag.
     */
    @Override
    public void addFlagToCurrentBus(IFlag flag) {
        if (currentBus != null) {
            // notify backend that a new flag has been added to currentBus
            IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
            waftAdapter.flagBus(this.currentBus, flag);
        }
    }

    @Override
    public boolean isOnBus() {
        return currentBus != null;
    }

    @Override
    public IBus getCurrentBus() {
        return this.currentBus;
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof NewBusNearbyEvent) {
            handleNewBusNearbyEvent((NewBusNearbyEvent) event);
        }
    }

    private void handleNewBusNearbyEvent(NewBusNearbyEvent event) {
        String dgw = event.getDGW();
        if (dgw == null) {
            currentBus = null;
        } else {
            //Perform AsyncTask that updates current bus
            new GetCurrentBusTask().execute(dgw);
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

    //Updates the current bus by getting a new one from BusFactory. Calls to BusFactory can no be made
    //on the main thread, so we use the async task.
    private class GetCurrentBusTaskgi extends AsyncTask<String, Void, IBus> {

        @Override
        protected IBus doInBackground(String... params) {
            String dgw = params[0];
            return BusFactory.getBus(dgw);
        }

        @Override
        protected void onPostExecute(IBus bus) {
            currentBus = bus;
            observableHelper.notifyObservers(new CurrentBusChangeEvent(currentBus));
        }
    }
}
