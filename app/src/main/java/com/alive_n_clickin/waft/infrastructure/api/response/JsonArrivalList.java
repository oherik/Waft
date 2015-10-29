package com.alive_n_clickin.waft.infrastructure.api.response;

import com.alive_n_clickin.waft.infrastructure.api.JsonJavaConverter;

import java.util.List;

import lombok.ToString;

/**
 * This class models the response from the Vasttrafik API regarding which vehicles that are headed
 * to a station. It's used in the {@link JsonJavaConverter} to convert the information into a java
 * object.
 *
 * @since 0.2
 */
@ToString
public class JsonArrivalList {
    private String noNamespaceSchemaLocation;
    private String serverTime;
    private String serverDate;
    private List<JsonArrival> Departure;

    public List<JsonArrival> getDepartures() {
        return this.Departure;
    }
}
