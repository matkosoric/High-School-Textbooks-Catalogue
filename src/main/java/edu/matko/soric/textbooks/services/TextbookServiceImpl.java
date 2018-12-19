package edu.matko.soric.textbooks.services;

import edu.matko.soric.textbooks.dao.TextbookRepository;
import edu.matko.soric.textbooks.entities.Textbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Matko Soric
 * @version 1.0
 */

@Service
public class TextbookServiceImpl implements TextbookService {


    @Autowired
    private TextbookRepository textbookRepository;


    @Override
    public List<Textbook> findAll() {

        return textbookRepository.findAll();

    }

    @Override
    public Optional<Textbook> getTextbookById(Long id) {

        return textbookRepository.findById(id);

    }

    @Override
    public Textbook saveTextbook(Textbook textbook) {

        return textbookRepository.save(textbook);

    }

    @Override
    public Boolean existsById(long id) {
        return textbookRepository.existsById(id);
    }

    @Override
    public void deleteTextbook(Long id) {
        textbookRepository.deleteById(id);
    }


}
