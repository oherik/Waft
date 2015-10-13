package com.alive_n_clickin.commutity.infrastructure.api.response;

import com.alive_n_clickin.commutity.infrastructure.api.JsonJavaConverter;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

/**
 * A class containing the different data for bus stops, as per the ElectriCity API. It's serializable
 * so it can be sent in intents, bundles, etc.
 *
 * The class is used within {@link JsonJavaConverter} to convert the json into a java object.
 *
 * @since 0.1
 */
@ToString public class JsonStop implements Serializable {

    /**
     * Used for making sure that a de-serialized file is compatible with this class.
     */
    private static final long serialVersionUID = 1337L;

    @Getter private String name;
    @Getter private long id;
    @Getter private double lat;
    @Getter private double lon;
    @Getter private char track;

}
