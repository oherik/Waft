package com.alive_n_clickin.commutity.infrastructure.api.response;

import com.alive_n_clickin.commutity.infrastructure.api.JsonJavaConverter;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

/**
 * This class models the response from the Vasttrafik API regarding which vehicles that are headed
 * to a station. It's used in the {@link JsonJavaConverter} to convert the information into a java
 * object.
 */
@ToString
public class JsonArrivalList {
    private String noNamespaceSchemaLocation;
    private String serverTime;
    private String serverDate;
    @Getter private List<JsonArrival> Departure;
}
