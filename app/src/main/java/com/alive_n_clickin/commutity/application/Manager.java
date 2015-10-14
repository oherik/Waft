package com.alive_n_clickin.commutity.application;

import android.content.Context;
import android.os.AsyncTask;

import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IVasttrafikAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.IWaftAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;
import com.alive_n_clickin.commutity.util.event.CantSearchForVehiclesEvent;
import com.alive_n_clickin.commutity.util.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObservableHelper;
import com.alive_n_clickin.commutity.util.event.IObserver;
import com.alive_n_clickin.commutity.util.event.NewBusNearbyEvent;
import com.alive_n_clickin.commutity.util.event.ObservableHelper;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

/**
 * An implementation of the IManager interface. This implementation listens to a NearbyBusScanner
 * for events regarding nearby buses to keep track of which bus the user is currently on.
 */
public class Manager implements IManager, IObserver {
    private IObservableHelper observableHelper = new ObservableHelper();
    private IVasttrafikAdapter vasttrafikAdapter;
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
        vasttrafikAdapter = ApiAdapterFactory.createVasttrafikAdapter();
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
            IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
            return waftAdapter.flagBus(this.currentBus, flag);
        }
        return false;
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
            return VehicleFactory.getBus(dgw);
        }

        @Override
        protected void onPostExecute(IElectriCityBus bus) {
            currentBus = bus;
            observableHelper.notifyObservers(new CurrentBusChangeEvent(currentBus));
        }
    }

    @Override
    public List<IArrivingVehicle> getVehicles(@NonNull IStop stop) {
        List<JsonArrival> jsonArrivals = vasttrafikAdapter.getVehiclesHeadedToStop(stop);
        List<IArrivingVehicle> arrivingVehicles = new ArrayList<>();
        if (jsonArrivals != null) {
            for(JsonArrival a : jsonArrivals) {
                arrivingVehicles.add(VehicleFactory.getArrivingVehicle(a));
            }
        }
        return arrivingVehicles;
    }

    @Override
    public List<IStop> searchForStops(@NonNull String searchQuery) {
        List<JsonStop> jsonStopResponse = vasttrafikAdapter.getSearchStops(searchQuery);
        List<IStop> stops = new ArrayList<>();
        for(JsonStop s : jsonStopResponse) {
            stops.add(StopFactory.getStop(s));
        }
        return stops;
    }

    public boolean canSearch() {
        return nearbyBusScanner.canSearch();
    }

    @Override
    public void searchForVehicles(Context context) {
        nearbyBusScanner.enableSearchingAndSearch();
    }
}
