package com.alive_n_clickin.waft.application;

import com.alive_n_clickin.waft.domain.IArrivingVehicle;
import com.alive_n_clickin.waft.domain.IElectriCityBus;
import com.alive_n_clickin.waft.domain.IFlag;
import com.alive_n_clickin.waft.domain.IStop;
import com.alive_n_clickin.waft.infrastructure.api.ConnectionException;
import com.alive_n_clickin.waft.util.event.IObservable;

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
     * Deleted the flag if the id is valid.
     * @param flag the flag to delete.
     * @return true if successful.
     */
    boolean deleteFlag(IFlag flag);

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
    List<IArrivingVehicle> getVehicles(IStop stop) throws ConnectionException;

    /**
     * Searches for stops based on a query string
     * @param searchQuery The string the search will be conducted on
     * @return  A list of stops matching the search query
     * @throws NullPointerException if the parameter is null
     */
    List<IStop> searchForStops(String searchQuery) throws ConnectionException;

    /**
     * @return true if the app can currently look for vehicles, false otherwise
     */
    boolean canSearch();

    /**
     * Perform a new search for vehicles. Listeners for these types of events will be notified when
     * the search completes.
     *
     * This method will enable the functions needed in the android system needed for searching, if they
     * are not already enabled.
     */
    void searchForVehicles();
}
