package com.alive_n_clickin.commutity.application;

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

// TODO: Documentation
public class BusManager implements IBusManager, IObserver {
    private IObservableHelper observableHelper = new ObservableHelper();

    private IBus currentBus = null;

    // TODO: Documentation
    public BusManager(NearbyBusScanner nearbyBusScanner) {
        nearbyBusScanner.addObserver(this);
    }

    // TODO: Documentation
    @Override
    public void addFlagToCurrentBus(IFlag flag) {
        if (currentBus != null) {
            // notify backend that a new flag has been added to currentBus
            IWaftAdapter waftAdapter = ApiAdapterFactory.createWaftAdapter();
            waftAdapter.flagBus(this.currentBus, flag);
        }
    }

    // TODO: Documentation
    @Override
    public IBus getCurrentBus() {
        return this.currentBus;
    }

    // TODO: Documentation
    @Override
    public void onEvent(IEvent event) {
        if (event instanceof NewBusNearbyEvent) {
            handleNewBusNearbyEvent((NewBusNearbyEvent) event);
        }
    }

    private void handleNewBusNearbyEvent(NewBusNearbyEvent event) {
        String DGW = event.getDGW();
        currentBus = BusFactory.getBus(DGW);
        observableHelper.notifyObservers(new CurrentBusChangeEvent(currentBus));
    }

    // TODO: Documentation
    @Override
    public void addObserver(IObserver observer) {
        observableHelper.addObserver(observer);
    }

    // TODO: Documentation
    @Override
    public void removeObserver(IObserver observer) {
        observableHelper.removeObserver(observer);
    }
}
