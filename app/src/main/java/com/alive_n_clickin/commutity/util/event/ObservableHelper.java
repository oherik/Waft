package com.alive_n_clickin.commutity.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the IObservableHelper interface.
 */
public class ObservableHelper implements IObservableHelper {
    private List<IObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(IEvent event) {
        for (IObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}
