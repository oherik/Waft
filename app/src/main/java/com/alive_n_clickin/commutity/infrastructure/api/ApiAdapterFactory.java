package com.alive_n_clickin.commutity.infrastructure.api;


public class ApiAdapterFactory {

    public static IElectricityAdapter createElectricityAdapter() {
        return new ElectricityAdapter();
    }

    public static IVasttrafikAdapter createVasttrafikAdapter() {
        return new VasttrafikAdapter();
    }

}
