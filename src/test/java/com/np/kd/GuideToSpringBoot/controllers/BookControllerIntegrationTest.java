package com.np.kd.GuideToSpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.np.kd.GuideToSpringBoot.TestDataUtils;
import com.np.kd.GuideToSpringBoot.domain.dto.BookDto;
import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import com.np.kd.GuideToSpringBoot.services.BookService;
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
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtils.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatSaveUpdateBookReturnsHttpStatus200OK() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        BookDto saveUpdateBookDto = TestDataUtils.createTestBookDToB(null);
        String createBookJson = objectMapper.writeValueAsString(saveUpdateBookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatCreateBookReturnsCreateBook() throws Exception {
        BookDto bookDto = TestDataUtils.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()));
    }

    @Test
    public void testThatSaveUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        BookDto saveUpdateBookDto = TestDataUtils.createTestBookDToB(null);
        String createBookJson = objectMapper.writeValueAsString(saveUpdateBookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(saveUpdateBookDto.getTitle()));
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListBooksReturnsListOfBook() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic"));
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200OKWhenBookExist() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenBookDoesNotExist() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpsStatus200Ok() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookA = TestDataUtils.createTestBookDtoA(null);
        testBookA.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(MockMvcRequestBuilders.patch("/books/" + testBookEntity.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtils.createTestBookDtoA(null);
        testBookA.setTitle("UPDATED");

        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED"));
    }

    @Test
    public void testThatGetBookReturnsBookWhenExists() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic"));
    }

    @Test
    public void testThatDeleteNonExistingBookReturns204NoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/kjkjddk")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteExistingBookReturns204NoContent() throws Exception {
        BookEntity testBookEntity = TestDataUtils.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.saveBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + savedBookEntity.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
