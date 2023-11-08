package com.np.kd.GuideToSpringBoot.repositories;

import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity,Long> {
}
