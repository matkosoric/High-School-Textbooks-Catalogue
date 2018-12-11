package edu.matko.soric.textbooks.controllers;

import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.services.TextbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TextbookController {

    @Autowired
    private TextbookService textbookService;

    @GetMapping("/textbooks")
    public List<Textbook> allTextbooks() {
        return textbookService.findAll();
    }

    @GetMapping("/textbooks/{id}")
    Optional<Textbook> getTextbookById(@PathVariable Long id) {
        return textbookService.getTextbookById(id);
    }

    @PostMapping ("/textbooks/new")
    public ResponseEntity<?> addTextbook (@Valid @RequestBody Textbook textbook, final BindingResult binding) {
        if (binding.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(binding.getFieldError().getDefaultMessage());
        }

        if (textbookService.existsById(textbook.getId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("HTTP/1.1 " + HttpStatus.CONFLICT.value() + " " + HttpStatus.CONFLICT.name());
        }

        return ResponseEntity
                        .ok()
                        .body(textbookService.saveTextbook(textbook));
    }

    @PostMapping ("/textbooks/edit/{id}")
    public ResponseEntity<?> editTextbook (@Valid @RequestBody Textbook textbook, @PathVariable Long id, final BindingResult binding) {

        if (binding.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(binding.getFieldError().getDefaultMessage());
        }

        if (!textbookService.existsById(textbook.getId())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("HTTP/1.1 " + HttpStatus.NOT_FOUND.value() + " " + HttpStatus.NOT_FOUND.name());
        }

            return ResponseEntity
                    .ok()
                    .body(textbookService.saveTextbook(textbook));
        }

    }
