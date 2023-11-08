package com.np.kd.GuideToSpringBoot.repositories;

import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity,String> {
}
