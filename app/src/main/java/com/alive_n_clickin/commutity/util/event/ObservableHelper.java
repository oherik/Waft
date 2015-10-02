package com.alive_n_clickin.commutity.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the IObservableHelper interface.
 *
 * @param <E> the type of event this ObservableHelper will send to all registered observers.
 */
public class ObservableHelper<E extends IEvent> implements IObservableHelper<E> {
    private List<IObserver<E>> observers = new ArrayList<>();

    @Override
    public void addObserver(IObserver<E> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver<E> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(E event) {
        for (IObserver<E> observer : observers) {
            observer.onEvent(event);
        }
    }
}
