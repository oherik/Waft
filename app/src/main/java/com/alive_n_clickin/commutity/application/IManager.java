package com.alive_n_clickin.commutity.application;

import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.util.event.IObservable;

import java.util.List;

/**
 * An interface for classes that manages communication between the view and the underlying systems.
 * Implementations of this interface should always keep track of what bus the user is currently on.
 *
 * Classes that implement this interface should be observable, and emit CurrentBusChangeEvents when
 * the bus the user is currently on changes.
 *
 * @since 0.2
 */
public interface IManager extends IObservable {
    /**
     * Flags the bus the user is currently on. Will not do anything if the user isn't currently on
     * a bus.
     *
     * @param flag the flag to flag the current bus with.
     */
    boolean addFlagToCurrentBus(IFlag flag);

    /**
     * @return true if the user is currently on a bus, otherwise false.
     */
    boolean isOnBus();

    /**
     * @return the bus the user is currently on.
     */
    IElectriCityBus getCurrentBus();

    /**
     * Returns the vehicles headed to a specified stop
     * @param stop The stop to base a search query on
     * @return  A list of the 20 first vehicles arriving to the stop
     * @throws NullPointerException if the parameter is null
     */
    List<IArrivingVehicle> getVehicles(IStop stop);

    /**
     * Searches for stops based on a query string
     * @param searchQuery The string the search will be conducted on
     * @return  A list of stops matching the search query
     * @throws NullPointerException if the parameter is null
     */
    List<IStop> searchForStops(String searchQuery);
}
