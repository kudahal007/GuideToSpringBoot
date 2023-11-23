package com.np.kd.GuideToSpringBoot.services;

import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity saveAuthor(AuthorEntity author);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);
}
