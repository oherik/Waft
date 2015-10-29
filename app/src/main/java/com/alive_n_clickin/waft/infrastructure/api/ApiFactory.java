package com.alive_n_clickin.waft.infrastructure.api;

/**
 * This class purpose is to provide access to the different API classes. Since the class returns
 * interfaces it would be easy to swap out any of the modules themselves. The user doesn't need to
 * know about the implementers directly.
 *
 * @since 1.0
 */
public class ApiFactory {
    /**
     * @return a new IElectriCityApi of the type ElectriCityApi.
     */
    public static IElectriCityApi createElectriCityApi() {
        return new ElectriCityApi();
    }

    /**
     * @return a new IWaftApi of the type WaftApi.
     */
    public static IWaftApi createWaftApi() {
        return new WaftApi();
    }

    /**
     * @return a new IVasttrafikApi of the type VasttrafikApi.
     */
    public static IVasttrafikApi createVasttrafikApi() {
        return new VasttrafikApi();
    }
}
