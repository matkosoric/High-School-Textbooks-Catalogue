package edu.matko.soric.textbooks.services;

import edu.matko.soric.textbooks.entities.Textbook;

import java.util.List;
import java.util.Optional;

/**
 * @author Matko Soric
 * @version 1.0
 */

public interface TextbookService {

    List<Textbook> findAll();

    Optional<Textbook> getTextbookById(Long id);

    Textbook saveTextbook(Textbook textbook);

    Boolean existsById(long id);

    void deleteTextbook(Long id);
}
