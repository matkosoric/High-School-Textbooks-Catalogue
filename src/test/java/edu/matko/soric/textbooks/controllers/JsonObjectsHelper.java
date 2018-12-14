package edu.matko.soric.textbooks.controllers;

import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.services.TextbookService;
import edu.matko.soric.textbooks.services.TextbookServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class JsonObjectsHelper {

    static JSONObject malformedJSON () throws Exception{

        JSONObject malformedJSON = new JSONObject();
        malformedJSON.put("title", "KNJIŽEVNOST 1 : čitanka za 1. razred četverogodišnjih strukovnih škola");
        malformedJSON.put("author", "Matko Sorić");
        malformedJSON.put("category", "udžbenik");
        malformedJSON.put("grade", "1");
        malformedJSON.put("publisher", "ALFA");
        malformedJSON.put("edition", null);
        malformedJSON.put("price", 90);

        return malformedJSON;

    }

    static JSONObject customIdTextbookJSON (Integer customId) throws Exception {

        JSONObject customIdTextbook = new JSONObject();
        customIdTextbook.put("id", customId);
        customIdTextbook.put("title", "KNJIŽEVNOST 1 : čitanka za 1. razred četverogodišnjih strukovnih škola");
        customIdTextbook.put("author", "Matko Sorić");
        customIdTextbook.put("category", "udžbenik");
        customIdTextbook.put("grade", "1");
        customIdTextbook.put("publisher", "ALFA");
        customIdTextbook.put("edition", null);
        customIdTextbook.put("price", 90);

        return customIdTextbook;

    }

}
