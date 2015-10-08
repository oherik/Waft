package com.alive_n_clickin.commutity.infrastructure.api;

import java.util.List;

import lombok.Getter;

/**
 * This class models the response from the Vasttrafik API regarding which vehicles that are headed
 * to a station. It's used in the {@link JsonJavaConverter} to convert the information into a java
 * object.
 */
public class ArrivalList {
    private String noNamespaceSchemaLocation;
    private String serverTime;
    private String serverDate;
    @Getter private List<ApiArrival> Departure;

    @Override
    public String toString() {
        return "DepartureBoard{" +
                "noNamespaceSchemaLocation='" + noNamespaceSchemaLocation + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", serverDate='" + serverDate + '\'' +
                ", Departure=" + Departure +
                '}';
    }
}
