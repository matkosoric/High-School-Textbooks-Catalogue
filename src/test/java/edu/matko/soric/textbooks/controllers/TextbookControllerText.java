package edu.matko.soric.textbooks.controllers;


import edu.matko.soric.textbooks.TextbooksCatalogueApp;
import edu.matko.soric.textbooks.services.TextbookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TextbooksCatalogueApp.class)
public class TextbookControllerText {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TextbookService textbookService;

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

//    @Test
//    public void listOneTextbook() throws Exception {
//
//        mockMvc.perform(
//                get("/")
//        )
//
//    }


}
