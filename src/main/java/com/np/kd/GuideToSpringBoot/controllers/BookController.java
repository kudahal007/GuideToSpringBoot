package com.np.kd.GuideToSpringBoot.controllers;

import com.np.kd.GuideToSpringBoot.domain.dto.BookDto;
import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import com.np.kd.GuideToSpringBoot.domain.mappers.Mapper;
import com.np.kd.GuideToSpringBoot.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity saveUpdateBookEntity = bookService.saveBook(isbn, bookEntity);
        BookDto saveUpdateBookDto = bookMapper.mapTo(saveUpdateBookEntity);
        if (bookExists) {
            return ResponseEntity.status(HttpStatus.OK).body(saveUpdateBookDto);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(saveUpdateBookDto);
        }
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn,
                                                     @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        return ResponseEntity.status(HttpStatus.OK).body(bookMapper.mapTo(updatedBookEntity));
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

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){
        bookService.deleteBook(isbn);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
