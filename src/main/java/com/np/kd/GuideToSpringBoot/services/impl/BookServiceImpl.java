package com.np.kd.GuideToSpringBoot.services.impl;

import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import com.np.kd.GuideToSpringBoot.repositories.BookRepository;
import com.np.kd.GuideToSpringBoot.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return (List<BookEntity>) bookRepository.findAll();
    }
}
