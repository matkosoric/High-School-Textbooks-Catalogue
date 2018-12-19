package edu.matko.soric.textbooks.dao;

import edu.matko.soric.textbooks.entities.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Matko Soric
 * @version 1.0
 */

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long> {
}
