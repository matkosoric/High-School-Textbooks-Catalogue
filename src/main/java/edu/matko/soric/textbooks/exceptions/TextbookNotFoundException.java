package edu.matko.soric.textbooks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TextbookNotFoundException extends RuntimeException {

    public TextbookNotFoundException(String exception) {
        super("The textbook with id " + exception + " was not found.");
    }

}
