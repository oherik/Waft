package com.alive_n_clickin.commutity.util.event;

/**
 * Created by mats on 01/10/15.
 */
public interface IObserver<E extends IEvent> {
    void onEvent(E event);
}
