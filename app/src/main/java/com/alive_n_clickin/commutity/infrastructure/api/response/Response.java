package com.alive_n_clickin.commutity.infrastructure.api.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A class for responses to HTTP requests. Responses have a status and a body.
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Response {
    @Getter private int status;
    @Getter @NonNull private String body;
}
