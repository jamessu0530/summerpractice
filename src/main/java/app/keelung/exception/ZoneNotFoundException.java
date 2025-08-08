package app.keelung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ZoneNotFoundException extends RuntimeException {
    public ZoneNotFoundException(String message) {
        super(message);
    }
}

