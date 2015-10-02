package com.alive_n_clickin.commutity.infrastructure.api;

import java.util.List;

/**
 * This class models the response from the Vasttrafik API and is used within {@link JsonJavaConverter} to convert into a java object.
 */
public class LocationList {
    private String noNamespaceSchemaLocation;
    private String serverTime;
    private String serverDate;
    private List<Stop> StopLocation;

    public List<Stop> getStopLocations(){
        return this.StopLocation;
    }

    @Override
    public String toString() {
        return "LocationList{" +
                "noNamespaceSchemaLocation='" + noNamespaceSchemaLocation + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", serverDate='" + serverDate + '\'' +
                ", StopLocation=" + StopLocation +
                '}';
    }
}
