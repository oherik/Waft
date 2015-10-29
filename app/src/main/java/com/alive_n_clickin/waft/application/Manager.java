package com.alive_n_clickin.waft.application;

import android.os.AsyncTask;

import com.alive_n_clickin.waft.application.api.ApiAdapterFactory;
import com.alive_n_clickin.waft.application.api.IVasttrafikAdapter;
import com.alive_n_clickin.waft.application.api.IWaftAdapter;
import com.alive_n_clickin.waft.domain.IArrivingVehicle;
import com.alive_n_clickin.waft.domain.IElectriCityBus;
import com.alive_n_clickin.waft.domain.IFlag;
import com.alive_n_clickin.waft.domain.IStop;
import com.alive_n_clickin.waft.util.event.CantSearchForVehiclesEvent;
import com.alive_n_clickin.waft.util.event.CurrentBusChangeEvent;
import com.alive_n_clickin.waft.util.event.IEvent;
import com.alive_n_clickin.waft.util.event.IObservableHelper;
import com.alive_n_clickin.waft.util.event.IObserver;
import com.alive_n_clickin.waft.util.event.NewBusNearbyEvent;
import com.alive_n_clickin.waft.util.event.ObservableHelper;

import java.util.List;

import lombok.NonNull;

/**
 * An implementation of the IManager interface. This implementation listens to a NearbyBusScanner
 * for events regarding nearby buses to keep track of which bus the user is currently on.
 *
 * @since 0.2
 */
public class Manager implements IManager, IObserver {
    private final IObservableHelper observableHelper = new ObservableHelper();
    private final IVasttrafikAdapter vasttrafikAdapter = ApiAdapterFactory.createVasttrafikAdapter();
    private final IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();

    private IElectriCityBus currentBus = null;
    private NearbyBusScanner nearbyBusScanner;

    /**
     * Initiates a new Manager that listens to the supplied NearbyBusScanner.
     *
     * @param nearbyBusScanner the NearbyBusScanner that this Manager should listen to for
     *                         events regarding nearby buses.
     */
    public Manager(NearbyBusScanner nearbyBusScanner) {
        this.nearbyBusScanner = nearbyBusScanner;
        this.nearbyBusScanner.addObserver(this);
    }

    /**
     * {@inheritDoc}<br><br>
     *
     * Sends a post request to our backend to flag the current bus with the supplied flag.
     */
    @Override
    public boolean addFlagToCurrentBus(IFlag flag) {
        if (currentBus != null) {
            // notify backend that a new flag has been added to currentBus
            boolean result = waftAdapter.flagBus(this.currentBus, flag);
            if (result) {
                //If the successful fetch the new bus from the Waft API.
                new GetCurrentBusTask().execute(this.getCurrentBus().getDGW());
            }
            return result;
        }
        return false;
    }

    @Override
    public boolean deleteFlag(IFlag flag) {
        return this.waftAdapter.deleteFlag(flag);
    }


    @Override
    public boolean isOnBus() {
        return currentBus != null;
    }

    @Override
    public IElectriCityBus getCurrentBus() {
        return this.currentBus;
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof NewBusNearbyEvent) {
            handleNewBusNearbyEvent((NewBusNearbyEvent) event);
        } else if (event instanceof CantSearchForVehiclesEvent) {
            handleCantSearchForVehiclesEvent((CantSearchForVehiclesEvent) event);
        }
    }

    private void handleCantSearchForVehiclesEvent(CantSearchForVehiclesEvent event) {
        currentBus = null;
        observableHelper.notifyObservers(event);
    }

    private void handleNewBusNearbyEvent(NewBusNearbyEvent event) {
        String dgw = event.getDGW();
        if (dgw == null) {
            currentBus = null;
            CurrentBusChangeEvent newBusEvent = new CurrentBusChangeEvent(currentBus);
            observableHelper.notifyObservers(newBusEvent);
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

    //Updates the current bus by getting a new one from VehicleFactory. Calls to VehicleFactory can no be made
    //on the main thread, so we use the async task.
    private class GetCurrentBusTask extends AsyncTask<String, Void, IElectriCityBus> {

        @Override
        protected IElectriCityBus doInBackground(String... params) {
            String dgw = params[0];
            return VehicleFactory.getElectriCityBus(dgw);
        }

        @Override
        protected void onPostExecute(IElectriCityBus bus) {
            currentBus = bus;
            observableHelper.notifyObservers(new CurrentBusChangeEvent(currentBus));
        }
    }

    @Override
    public List<IArrivingVehicle> getVehicles(@NonNull IStop stop) {
        return vasttrafikAdapter.getArrivalsForStop(stop);
    }

    @Override
    public List<IStop> searchForStops(@NonNull String searchQuery) {
        return vasttrafikAdapter.searchForStops(searchQuery);
    }

    public boolean canSearch() {
        return nearbyBusScanner.canSearch();
    }

    @Override
    public void searchForVehicles() {
        nearbyBusScanner.enableSearchingAndSearch();
    }
}
