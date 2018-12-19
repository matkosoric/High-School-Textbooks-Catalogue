package edu.matko.soric.textbooks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TextbookConflictException extends RuntimeException {

    public TextbookConflictException() {
        super("Id conflict with the existing textbook in the database.");
    }
}
