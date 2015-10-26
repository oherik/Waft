package com.alive_n_clickin.commutity.application.api;


/**
 * This class purpose is to provide access to the different adapters, where you can connect to the different API:s.
 * Since the class returns interfaces it would be easy to swap out any of the modules themselves.
 * The user doesn't need to know about the implementers directly.
 *
 * @since 0.1
 */
public class ApiAdapterFactory {

    /**
     * @return a new IElectriCityAdapter of the type ElectriCityAdapter.
     */
    public static IElectricityAdapter createElectricityAdapter() {
        return new ElectricityAdapter();
    }

    /**
     * @return a new IVasttrafikAdapter of the type VasttrafikAdapter.
     */
    public static IVasttrafikAdapter createVasttrafikAdapter() {
        return new VasttrafikAdapter();
    }

    /**
     * @return a new IWaftAdapter of the type WaftAdapter.
     */
    public static IWaftAdapter createWaftAdapter() {
        return new WaftAdapter();
    }

}
