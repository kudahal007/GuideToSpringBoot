package com.np.kd.GuideToSpringBoot.controllers;

import com.np.kd.GuideToSpringBoot.domain.dto.BookDto;
import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import com.np.kd.GuideToSpringBoot.domain.mappers.Mapper;
import com.np.kd.GuideToSpringBoot.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.mapTo(savedBookEntity));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> listBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        List<BookDto> bookDtos = bookEntities.stream().map(bookMapper::mapTo).toList();
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> bookEntity = bookService.findOne(isbn);
        return bookEntity.map(book -> ResponseEntity.status(HttpStatus.OK).body(bookMapper.mapTo(book)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
