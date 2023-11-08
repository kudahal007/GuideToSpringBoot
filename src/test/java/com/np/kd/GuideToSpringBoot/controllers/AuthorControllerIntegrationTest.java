package com.np.kd.GuideToSpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.np.kd.GuideToSpringBoot.TestDataUtils;
import com.np.kd.GuideToSpringBoot.domain.dto.AuthorDto;
import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import com.np.kd.GuideToSpringBoot.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtils.createTestAuthorEntityA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtils.createTestAuthorEntityA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(80));
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        authorService.createAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(80));
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200OkWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        authorService.createAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenAuthorNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityB();
        authorService.createAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/"+1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Thomas Cronin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(44));
    }
}
