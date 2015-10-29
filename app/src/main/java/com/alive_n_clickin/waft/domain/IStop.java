package com.alive_n_clickin.waft.domain;

/**
 * An interface for bus and tram stops. A stop should be able to provide a name and a unique ID
 * that can be used to identify it when using Västtrafik's API.
 *
 * @since 0.2
 */
public interface IStop {
    /**
     * @return the name that identifies this stop.
     */
    String getName();

    /**
     * @return the unique ID that is used to identify this stop when using Västtrafik's API.
     */
    long getId();
}
