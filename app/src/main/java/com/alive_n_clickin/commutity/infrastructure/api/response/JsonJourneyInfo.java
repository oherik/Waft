package com.alive_n_clickin.commutity.infrastructure.api.response;

import java.io.Serializable;

import lombok.Getter;

/**
 * @since 0.2
 */
public class JsonJourneyInfo implements Serializable {
    @Getter private String resourceSpec;
    @Getter private long timestamp;
    @Getter private String value;
    @Getter private String gateway;
}
