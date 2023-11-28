package com.np.kd.GuideToSpringBoot.repositories;

import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<BookEntity,String>, PagingAndSortingRepository<BookEntity,String> {
}
