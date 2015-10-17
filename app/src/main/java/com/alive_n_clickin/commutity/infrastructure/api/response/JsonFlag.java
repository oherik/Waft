package com.alive_n_clickin.commutity.infrastructure.api.response;

import lombok.Getter;

/**
 * A class to model the response from the Waft API.
 *
 * @since 0.2
 */
public class JsonFlag {
    @Getter private int flagType;
    @Getter private String comment;
}
