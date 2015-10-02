package com.alive_n_clickin.commutity.util.event;

/**
 * Created by mats on 01/10/15.
 */
public interface IObservable<E extends IEvent> {
    void addObserver(IObserver<E> observer);
    void removeObserver(IObserver<E> observer);
}
