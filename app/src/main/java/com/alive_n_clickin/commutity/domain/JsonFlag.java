package com.alive_n_clickin.commutity.domain;

import lombok.Getter;

/**
 * A class to model the response from the Waft API.
 */
public class JsonFlag {
    @Getter private int flagType;
    @Getter private String comment;
}
