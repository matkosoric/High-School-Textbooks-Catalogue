package edu.matko.soric.textbooks.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.matko.soric.textbooks.TextbooksCatalogueApp;
import edu.matko.soric.textbooks.entities.Textbook;
import edu.matko.soric.textbooks.services.TextbookService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TextbooksCatalogueApp.class)
public class TextbookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TextbookService textbookService;

    Random rand = new Random();
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
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.page.totalElements", is(textbookService.findAll().size())));
    }

    @Test
    public void getOneTextbook() throws Exception {

        List<Textbook> textbookList = textbookService.findAll();
        randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));

        mockMvc.perform(
                get("/textbooks/" + randomTextbook.getId())
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(randomTextbook)));
    }

    @Test
    public void nonExistingTextbook() throws Exception {

        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        mockMvc.perform(
                get("/textbooks/" + nonexistingId)
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));
    }

    @Test
    public void newTextbookMalformedRequest() throws Exception {

        mockMvc.perform(
                post("/textbooks/new")
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonObjectsHelper.malformedJSON().toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));

    }

    @Test
    public void newTextbookExistingId() throws Exception {

        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String existingTextbookJSON = ow.writeValueAsString(randomTextbook);

        mockMvc.perform(
                post("/textbooks/new")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(existingTextbookJSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"));
    }


    @Test
    public void newTextbook() throws Exception {

        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        JSONObject newTextbookJSON = JsonObjectsHelper.customIdTextbookJSON(nonexistingId);

        mockMvc.perform(
                post("/textbooks/new")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(newTextbookJSON.toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }


    @Test
    public void editTextbookMalformedRequestWronPathPathExistingObject() throws Exception {

        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String existingTextbookJSON = ow.writeValueAsString(randomTextbook);

        mockMvc.perform(
                post("/textbooks/edit/" + nonexistingId)
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(existingTextbookJSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));

    }


    @Test
    public void editTextbookMalformedRequestWrongPathWrongObjectMatchingId() throws Exception {

        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (!(nonexistingId > 0)) {
            if (!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        JSONObject nonExistingTextbookJSON = JsonObjectsHelper.customIdTextbookJSON(nonexistingId);

        mockMvc.perform(
                post("/textbooks/edit/" + nonExistingTextbookJSON.get("id"))
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(nonExistingTextbookJSON.toString()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }


    @Test
    public void editTextbookMalformedRequestGoodPathGoodObjectIdMismatch() throws Exception {

        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Textbook randomTextbook2 = textbookList.get(rand.nextInt(textbookList.size()));
        String randomTextbookJSON2 = ow.writeValueAsString(randomTextbook2);

        mockMvc.perform(
                post("/textbooks/edit/" + randomTextbook.getId())
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(randomTextbookJSON2))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }

    @Test
    public void editTextbookGoodRequest() throws Exception {

        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String randomTextbookJSON = ow.writeValueAsString(randomTextbook);

        mockMvc.perform(
                post("/textbooks/edit/" + randomTextbook.getId())
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(randomTextbookJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteTextbookNonexistingId() throws Exception {

        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (!(nonexistingId > 0)) {
            if (!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        mockMvc.perform(
                delete("/textbooks/edit/" + nonexistingId)
                        .contextPath(contextPath))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));

    }


    @Test
    public void deleteTextbookGoodRequest() throws Exception {

        //first, create instance for deletion
        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        JSONObject newTextbookJSON = JsonObjectsHelper.customIdTextbookJSON(nonexistingId);

        mockMvc.perform(
                post("/textbooks/new")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(newTextbookJSON.toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        //delete
        mockMvc.perform(
                delete("/textbooks/edit/" + newTextbookJSON.get("id"))
                        .contextPath(contextPath))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

    }

}
