package com.alive_n_clickin.commutity.infrastructure.api;


/**
 * This class purpose is to provide access to the different adapters, where you can connect to the different API:s.
 * Since the class returns interfaces it would be easy to swap out any of the modules themselves.
 * The user doesn't need to know about the implementers directly.
 *
 * The current adapters are:
 * {@link IVasttrafikAdapter}
 * {@link IElectricityAdapter}
 * @since 0.1
 */
public class ApiAdapterFactory {

    public static IElectricityAdapter createElectricityAdapter() {
        return new ElectricityAdapter();
    }

    public static IVasttrafikAdapter createVasttrafikAdapter() {
        return new VasttrafikAdapter();
    }

    public static IWaftAdapter createWaftAdapter() {
        return new WaftAdapter();
    }

}
