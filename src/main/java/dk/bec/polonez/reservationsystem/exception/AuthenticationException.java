package dk.bec.polonez.reservationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationException extends ResponseStatusException {

    public AuthenticationException(HttpStatus status, String message) {
        super(status, message);
    }
}
