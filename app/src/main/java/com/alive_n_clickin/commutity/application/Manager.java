package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.event.NewBusNearbyEvent;
import com.alive_n_clickin.commutity.infrastructure.api.ApiAdapterFactory;
import com.alive_n_clickin.commutity.infrastructure.api.IVasttrafikAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.IWaftAdapter;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonArrival;
import com.alive_n_clickin.commutity.infrastructure.api.response.JsonStop;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObservableHelper;
import com.alive_n_clickin.commutity.util.event.IObserver;
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

    /**
     * Initiates a new Manager that listens to the supplied NearbyBusScanner.
     *
     * @param nearbyBusScanner the NearbyBusScanner that this Manager should listen to for
     *                         events regarding nearby buses.
     */
    public Manager(NearbyBusScanner nearbyBusScanner) {
        nearbyBusScanner.addObserver(this);
        vasttrafikAdapter = ApiAdapterFactory.createVasttrafikAdapter();
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
    public IElectriCityBus getCurrentBus() {
        return this.currentBus;
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof NewBusNearbyEvent) {
            handleNewBusNearbyEvent((NewBusNearbyEvent) event);
        }
    }

    private void handleNewBusNearbyEvent(NewBusNearbyEvent event) {
        String DGW = event.getDGW();
        if (DGW != null) {
            currentBus = VehicleFactory.getBus(DGW);
        } else {
            currentBus = null;
        }

        observableHelper.notifyObservers(new CurrentBusChangeEvent(currentBus));
    }

    @Override
    public void addObserver(IObserver observer) {
        observableHelper.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observableHelper.removeObserver(observer);
    }

    @Override
    public List<IArrivingVehicle> getVehicles(@NonNull IStop stop){
        List<JsonArrival> jsonArrivals = vasttrafikAdapter.getVehiclesHeadedToStop(stop);
        List<IArrivingVehicle> arrivingVehicles = new ArrayList<>();
        for(JsonArrival a : jsonArrivals){
            arrivingVehicles.add(VehicleFactory.getArrivingVehicle(a));
        }
        return arrivingVehicles;
    }

    @Override
    public List<IStop> searchForStops(@NonNull String searchQuery) {
        List<JsonStop> jsonStopResponse = vasttrafikAdapter.getSearchStops(searchQuery);
        List<IStop> stops = new ArrayList<>();
        for(JsonStop s : jsonStopResponse){
            stops.add(StopFactory.getStop(s));
        }
        return stops;
    }
}
