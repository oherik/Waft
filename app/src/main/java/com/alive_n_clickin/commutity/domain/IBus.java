package com.alive_n_clickin.commutity.domain;

import java.util.List;

/**
 * An interface for modelling a bus. A bus should be immutable. All methods that returns
 * mutable objects should return copies of said object, to ensure immutability.
 */
public interface IBus {
    /**
     * @return the Device Gateway id of this bus. This ID is used by ElectriCity to identify the bus.
     */
    String getDGW();

    // TODO: Documentation
    String getDestination();
    String getJourneyName();
    String getRouteNumber();

    /**
     * @return a list of all flags on this bus. This method should never return null. If the bus
     * doesn't have anyg flags, it should return an empty list.
     */
    List<IFlag> getFlags();
}
