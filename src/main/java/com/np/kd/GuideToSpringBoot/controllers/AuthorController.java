package com.np.kd.GuideToSpringBoot.controllers;

import com.np.kd.GuideToSpringBoot.domain.dto.AuthorDto;
import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import com.np.kd.GuideToSpringBoot.domain.mappers.Mapper;
import com.np.kd.GuideToSpringBoot.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.mapTo(savedAuthorEntity));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> listAuthors() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        List<AuthorDto> authorDtos = authorEntities.stream().map(authorMapper::mapTo).toList();
        return ResponseEntity.status(HttpStatus.OK).body(authorDtos);
    }
}
