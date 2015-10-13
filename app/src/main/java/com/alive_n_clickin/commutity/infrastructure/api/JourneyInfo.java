package com.alive_n_clickin.commutity.infrastructure.api;

import java.io.Serializable;

import lombok.Getter;

/**
 * @author hjorthjort
 *         Created 08/10/15
 *
 * @since 0.2
 */
public class JourneyInfo implements Serializable {
    @Getter private String resourceSpec;
    @Getter private long timestamp;
    @Getter private String value;
    @Getter private String gateway;
}
