package com.np.kd.GuideToSpringBoot.services;

import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Long id);
}
