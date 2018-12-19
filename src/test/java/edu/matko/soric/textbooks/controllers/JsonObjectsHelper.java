package edu.matko.soric.textbooks.controllers;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonObjectsHelper {

    static JSONObject malformedJSON() throws Exception {

        JSONObject malformedJSON = new JSONObject();
        malformedJSON.put("title", "KNJIŽEVNOST 1 : čitanka za 1. razred četverogodišnjih strukovnih škola");
        malformedJSON.put("city", "Zagreb");
        malformedJSON.put("category", "udžbenik");
//        malformedJSON.put("grade", "1");
        malformedJSON.put("publisher", "ALFA");
        malformedJSON.put("edition", null);
        malformedJSON.put("price", 88);

        return malformedJSON;

    }

    static JSONObject customIdTextbookJSON(Integer customId) throws Exception {

        JSONObject customIdTextbook = new JSONObject();
        customIdTextbook.put("id", customId);
        customIdTextbook.put("title", "KNJIŽEVNOST 1 : čitanka za 1. razred četverogodišnjih strukovnih škola");
        customIdTextbook.put("author", "Daniela Novoselić, Ljiljana Vidović");
        customIdTextbook.put("category", "udžbenik");
        customIdTextbook.put("grade", "1");
        customIdTextbook.put("publisher", "ALFA");
        customIdTextbook.put("edition", null);
        customIdTextbook.put("price", 77);

        return customIdTextbook;

    }

}
