package com.alive_n_clickin.commutity.infrastructure.api;

import java.util.List;

/**
 * Created by OscarEvertsson on 30/09/15.
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
