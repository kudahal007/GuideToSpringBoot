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

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthorService authorService;

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
        authorService.saveAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Abigail Rose"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].age").value(80));
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200OkWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        authorService.saveAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/" + 1)
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
        authorService.saveAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Thomas Cronin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(44));
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200OKWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.saveAuthor(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDtoA();
        String authorDtoJsonA = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJsonA))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenAuthorNotExists() throws Exception {
        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDtoA();
        String authorDtoJsonA = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJsonA))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(testAuthorEntity);
        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDtoB();
        String jsonTestAuthorDto = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + savedAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestAuthorDto)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthorEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge()));
    }

    @Test
    public void testThatPartialUpdateAuthorReturns200OK() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        authorService.saveAuthor(testAuthorEntity);
        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDtoA();
        String jsonTestAuthorDto = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/" + testAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestAuthorDto))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsUpdateAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(testAuthorEntity);
        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDtoA();
        String jsonTestAuthorDto = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/" + testAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestAuthorDto))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthorEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge()));
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/9999")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.saveAuthor(testAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/"+savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
