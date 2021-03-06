package edu.matko.soric.textbooks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Matko Soric
 * @version 1.0
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TextbookIdMismatchException extends RuntimeException {

    public TextbookIdMismatchException() {
        super("Id mismatch between path and JSON object.");
    }

}
