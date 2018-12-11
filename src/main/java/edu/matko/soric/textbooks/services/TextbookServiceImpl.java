package edu.matko.soric.textbooks.services;

import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.repository.TextbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TextbookServiceImpl implements TextbookService {

    @Autowired
    private TextbookRepository textbookRepository;

    @Override
    public List<Textbook> findAll() {

        List<Textbook> allTextbooks = textbookRepository.findAll();
        return allTextbooks;

    }

    @Override
    public Optional<Textbook> getTextbookById(Long id) {
        return textbookRepository.findById(id);
    }
}
