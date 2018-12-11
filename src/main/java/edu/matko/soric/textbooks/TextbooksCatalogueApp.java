package edu.matko.soric.textbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Matko Soric
 * @version 1.0
 */

@SpringBootApplication
public class TextbooksCatalogueApp {
    public static void main(String[] args) {
        SpringApplication.run(TextbooksCatalogueApp.class, args);
    }

}
