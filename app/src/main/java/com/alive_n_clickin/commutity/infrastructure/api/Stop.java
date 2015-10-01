package com.alive_n_clickin.commutity.infrastructure.api;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by OscarEvertsson on 01/10/15.
 */
@ToString public class Stop {

    @Getter private String name;
    @Getter private long id;
    @Getter private double lat;
    @Getter private double lon;
    @Getter private char track;

}
