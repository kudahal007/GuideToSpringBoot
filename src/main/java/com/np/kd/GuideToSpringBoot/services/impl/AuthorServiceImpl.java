package com.np.kd.GuideToSpringBoot.services.impl;

import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import com.np.kd.GuideToSpringBoot.repositories.AuthorRepository;
import com.np.kd.GuideToSpringBoot.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return (List<AuthorEntity>) authorRepository.findAll();
    }
}
