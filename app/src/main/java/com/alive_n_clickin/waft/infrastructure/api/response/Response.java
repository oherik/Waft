package com.alive_n_clickin.waft.infrastructure.api.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * A class for responses to HTTP requests. Responses have a status and a body.
 *
 * @since 1.0
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode
@ToString
public class Response {
    @Getter private int status;
    @Getter @NonNull private String body;

    /**
     * Interprets a HTTP status code.
     *
     * @return true if the request that generated this response was ok (status is 200), otherwise false.
     */
    public boolean wasRequestSuccessful() {
        switch (this.status) {
            case 400:
                return false;
            case 200:
                return true;
        }
        return false;
    }
}
