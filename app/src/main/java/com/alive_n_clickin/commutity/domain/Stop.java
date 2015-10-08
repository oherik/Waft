package com.alive_n_clickin.commutity.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A simple implementation of the Stop interface.
 */
@EqualsAndHashCode
@ToString
public class Stop implements IStop {
    @Getter private final String name;
    @Getter private final long id;

    /**
     * Constructor.
     *
     * @param name the name that identifies this stop.
     * @param id the unique id that identifies this stop.
     */
    public Stop(@NonNull String name, long id) {
        this.name = name;
        this.id = id;
    }
}
