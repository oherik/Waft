package com.alive_n_clickin.commutity.infrastructure.api.response;

import lombok.Getter;

/**
 * A class for responses to HTTP requests. Responses have a status and a body.
 */
public class Response {
    @Getter private int status;
    @Getter private String body;
}
