package edu.matko.soric.textbooks.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matko.soric.textbooks.TextbooksCatalogueApp;
import edu.matko.soric.textbooks.dao.TextbookRepository;
import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.services.TextbookService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import edu.matko.soric.textbooks.utils.TextbookUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TextbooksCatalogueApp.class)
public class TextbookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TextbookService textbookService;

    @Autowired
    private TextbookRepository textbookRepository;

//    @Autowired
//    TextbookUtils textbookUtils;
    Textbook randomTextbook;
    Integer nonexistingId = 0;

    private String contextPath;

    @Before
    public void setUp() {
        contextPath = webApplicationContext.getServletContext().getContextPath();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void listAllTextbooks() throws Exception {

        mockMvc.perform(
                get("/textbooks")
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.page.totalElements", is(textbookService.findAll().size())));
    }

    @Test
    public void getOneTextbook() throws Exception {

        Random rand2 = new Random();
        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand2.nextInt(textbookList.size()));

        mockMvc.perform(
                get("/textbooks/" + randomTextbook.getId())
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(randomTextbook)));
    }

    @Test
    public void nonExistingTextbook() throws Exception {

        Random rand = new Random();
        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        mockMvc.perform(
                get("/textbooks/" + nonexistingId)
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void newTextbookMalformedRequest() throws Exception {

        JSONObject json = new JSONObject();
        json.put("title", "KNJIŽEVNOST 1 : čitanka za 1. razred četverogodišnjih strukovnih škola");
        json.put("author", "Matko Sorić");
        json.put("category", "udžbenik");
        json.put("grade", "1");
        json.put("publisher", "ALFA");
        json.put("edition", null);
        json.put("price", 90);

        mockMvc.perform(
                post("/textbooks/new")
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));

    }

    @Test
    public void newTextbookExistingId() throws Exception {

        JSONObject json = new JSONObject();
        json.put("title", "KNJIŽEVNOST 1 : čitanka za 1. razred četverogodišnjih strukovnih škola");
        json.put("author", "Matko Sorić");
        json.put("category", "udžbenik");
        json.put("grade", "1");
        json.put("publisher", "ALFA");
        json.put("edition", null);
        json.put("price", 90);

        mockMvc.perform(
                post("/textbooks/new")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));


    }


    @Test
    public void newTextbook() throws Exception {


    }


}
