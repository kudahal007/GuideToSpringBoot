package com.np.kd.GuideToSpringBoot.services;

import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    BookEntity saveBook(String isbn,BookEntity bookEntity);

    Page<BookEntity>findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void deleteBook(String isbn);
}
