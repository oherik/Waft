package com.alive_n_clickin.commutity.infrastructure.api.response;

import com.alive_n_clickin.commutity.infrastructure.api.JsonJavaConverter;

import java.util.List;

import lombok.ToString;

/**
 * This class models the response from the Vasttrafik API and is used within {@link JsonJavaConverter} to convert into a java object.
 *
 * @since 0.1
 */
@ToString
public class JsonStopList {
    private String noNamespaceSchemaLocation;
    private String serverTime;
    private String serverDate;
    private List<JsonStop> StopLocation;

    public List<JsonStop> getStopLocations() {
        return this.StopLocation;
    }
}
