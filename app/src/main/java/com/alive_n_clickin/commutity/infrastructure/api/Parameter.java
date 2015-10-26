package com.alive_n_clickin.commutity.infrastructure.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Created by mats on 26/10/15.
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Parameter {
    @Getter @NonNull String key;
    @Getter @NonNull String value;
}
