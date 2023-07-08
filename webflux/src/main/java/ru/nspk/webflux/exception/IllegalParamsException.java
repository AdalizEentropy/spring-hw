package ru.nspk.webflux.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class IllegalParamsException extends ResponseStatusException {

    public IllegalParamsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
