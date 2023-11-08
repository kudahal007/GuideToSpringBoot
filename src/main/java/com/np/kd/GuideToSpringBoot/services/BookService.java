package com.np.kd.GuideToSpringBoot.services;

import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(String isbn,BookEntity bookEntity);

    List<BookEntity> findAll();
}
