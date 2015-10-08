package com.alive_n_clickin.commutity.infrastructure.api;

import java.io.Serializable;

/**
 * @author hjorthjort
 *         Created 08/10/15
 */
public class JourneyInfo implements Serializable {
    String resourceSpec;
    long timestamp;
    String value;
    String gatewayId;
}
