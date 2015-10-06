package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.util.event.IObservable;

// TODO: Documentation
public interface IBusManager extends IObservable {
    // TODO: Documentation
    void addFlagToCurrentBus(IFlag flag);
    boolean isOnBus();
    IBus getCurrentBus();
}
