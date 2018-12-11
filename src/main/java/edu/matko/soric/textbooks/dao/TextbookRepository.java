package edu.matko.soric.textbooks.dao;

import edu.matko.soric.textbooks.entities.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long> {
}
