package com.np.kd.GuideToSpringBoot.services;

import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);
    List<AuthorEntity> findAll();
}
