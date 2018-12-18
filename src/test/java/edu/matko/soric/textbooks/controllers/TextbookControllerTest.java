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
    public void givenNothing_whenGetTextbooks_then200IsReceived() throws Exception {

        // Given

        // When
        mockMvc.perform(
                get("/textbooks")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andExpect(jsonPath("$.page.totalElements", is(textbookService.findAll().size())));
    }

    @Test
    public void givenExistingTextbookId_whenGetRequest_then200isReceived() throws Exception {

        // Given
        List<Textbook> textbookList = textbookService.findAll();
        randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));

        // When
        mockMvc.perform(
                get("/textbooks/" + randomTextbook.getId())
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(randomTextbook)));
    }

    @Test
    public void givenNonExistingTextbookId_whenGetRequest_then404isReceived() throws Exception {

        // Given
        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        // When
        mockMvc.perform(
                get("/textbooks/" + nonexistingId)
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenMalformedTextbookJSON_whenCreateTextbook_then400isReceived() throws Exception {

        // Given
        String malformedTextbookJson = JsonObjectsHelper.malformedJSON().toString();

        // When
        mockMvc.perform(
                post("/textbooks/new")
                .contextPath(contextPath)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(malformedTextbookJson))
                .andDo(print())
                // Then
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenNewTextbookHavingExistingId_whenCreate_then409isReceived() throws Exception {

        // Given
        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String existingTextbookJSON = ow.writeValueAsString(randomTextbook);

        // When
        mockMvc.perform(
                post("/textbooks/new")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(existingTextbookJSON))
                .andDo(print())
                // Then
                .andExpect(status().isConflict());
    }


    @Test
    public void givenNewTextbook_whenCreateNewEntry_then201isReceived() throws Exception {


        // Given
        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (! (nonexistingId > 0)) {
            if(!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }
        JSONObject newTextbookJSON = JsonObjectsHelper.customIdTextbookJSON(nonexistingId);

        // When
        mockMvc.perform(
                post("/textbooks/new")
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(newTextbookJSON.toString()))
                .andDo(print())
                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }


    @Test
    public void givenExistingTextbookAndNonExistingPath_whenEditNonExistingId_then400isReceived() throws Exception {

        // Given
        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (!(nonexistingId > 0)) {
            if (!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String existingTextbookJSON = ow.writeValueAsString(randomTextbook);

        // When
        mockMvc.perform(
                post("/textbooks/edit/" + nonexistingId)
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(existingTextbookJSON))
                .andDo(print())
                // Then
                .andExpect(status().isBadRequest());
    }


    @Test
    public void givenNonExistingTextbook_whenEditNonExistingId_then404isReceived() throws Exception {

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
                .andExpect(status().isNotFound());
    }


    @Test
    public void givenExistingTextbookAndExistingId_whenEditIdMismatch_then400isReceived() throws Exception {

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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenExistingTextbook_whenEditId_then200isReceived() throws Exception {

        // Given
        List<Textbook> textbookList = textbookService.findAll();
        Textbook randomTextbook = textbookList.get(rand.nextInt(textbookList.size()));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String randomTextbookJSON = ow.writeValueAsString(randomTextbook);

        // When
        mockMvc.perform(
                post("/textbooks/edit/" + randomTextbook.getId())
                        .contextPath(contextPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(randomTextbookJSON))
                .andDo(print())
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void givenNonExistingTextbook_whenDelete_then404isReceived() throws Exception {

        // Given
        List<Long> exclude = textbookService.findAll().stream().map(textbook -> textbook.getId()).collect(Collectors.toList());
        while (!(nonexistingId > 0)) {
            if (!exclude.contains(rand))
                nonexistingId = rand.nextInt();
        }

        // When
        mockMvc.perform(
                delete("/textbooks/delete/" + nonexistingId)
                        .contextPath(contextPath))
                .andDo(print())
                // Then
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));

    }


    @Test
    public void givenExistingTextbook_whenDelete_then204isReceived() throws Exception {

        // Given
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

        // When
        mockMvc.perform(
                delete("/textbooks/delete/" + newTextbookJSON.get("id"))
                        .contextPath(contextPath))
                .andDo(print())
                // Then
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

    }

}
