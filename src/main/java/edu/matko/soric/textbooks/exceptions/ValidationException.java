package edu.matko.soric.textbooks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Matko Soric
 * @version 1.0
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    public ValidationException(String exception) {
        super("Validation exception: " + exception);
    }

}
