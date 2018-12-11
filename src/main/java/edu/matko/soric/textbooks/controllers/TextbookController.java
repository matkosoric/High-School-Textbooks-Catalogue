package edu.matko.soric.textbooks.controllers;

import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.services.TextbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


}
