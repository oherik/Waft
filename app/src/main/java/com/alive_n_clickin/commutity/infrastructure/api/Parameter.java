package com.alive_n_clickin.commutity.infrastructure.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A class for parameters. A parameter has a key and a value.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode
@ToString
public class Parameter {
    @Getter @NonNull String key;
    @Getter @NonNull String value;
}
