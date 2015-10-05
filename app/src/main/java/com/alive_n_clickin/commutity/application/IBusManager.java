package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.util.event.IObservable;

public interface IBusManager extends IObservable<CurrentBusChangeEvent> {
    void addFlagToCurrentBus(IFlag flag);
    IBus getCurrentBus();
}
