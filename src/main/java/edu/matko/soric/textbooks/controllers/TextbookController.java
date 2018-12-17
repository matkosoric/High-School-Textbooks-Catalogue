package edu.matko.soric.textbooks.controllers;

import edu.matko.soric.textbooks.exceptions.*;
import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.services.TextbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/textbooks")
public class TextbookController {

    @Autowired
    private TextbookService textbookService;

    @GetMapping("/")
    public List<Textbook> allTextbooks() {
        return textbookService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<?> getTextbookById(@PathVariable Long id) {

        if (!textbookService.existsById(id)) {
            throw new TextbookNotFoundException(id.toString());}

        return ResponseEntity
                .ok()
                .body(textbookService.getTextbookById(id));
    }

    @PostMapping ("/new")
    public ResponseEntity<?> addTextbook (@Valid @RequestBody Textbook textbook, final BindingResult binding) {
        if (binding.hasErrors()) {
            throw new ValidationException(binding.getFieldError().getDefaultMessage());
        }

        if (textbookService.existsById(textbook.getId())) {
            throw new TextbookConflictException();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(textbookService.saveTextbook(textbook));
    }

    @PostMapping ("/edit/{id}")
    public ResponseEntity<?> editTextbook (@Valid @RequestBody Textbook textbook, @PathVariable Long id, final BindingResult binding) {

        if (binding.hasErrors()) {
            throw new ValidationException(binding.getFieldError().getDefaultMessage());
        }

        if ((!textbook.getId().equals(id))) {
            throw new TextbookIdMismatchException();
        }

        if (!textbookService.existsById(textbook.getId())) {
            throw new TextbookNotFoundException(id.toString());
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(textbookService.saveTextbook(textbook));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTextbook (@Valid @PathVariable Long id) {

        if (!textbookService.existsById(id)) {
            throw new TextbookNotFoundException(id.toString());
        }

        textbookService.deleteTextbook(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(null);
    }

}
