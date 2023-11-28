package com.np.kd.GuideToSpringBoot.repositories;

import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity,Long>, PagingAndSortingRepository<AuthorEntity,Long> {
}
