package com.np.kd.GuideToSpringBoot.controllers;

import com.np.kd.GuideToSpringBoot.domain.dto.AuthorDto;
import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import com.np.kd.GuideToSpringBoot.domain.mappers.Mapper;
import com.np.kd.GuideToSpringBoot.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.mapTo(savedAuthorEntity));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> listAuthors() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        List<AuthorDto> authorDtos = authorEntities.stream().map(authorMapper::mapTo).toList();
        return ResponseEntity.status(HttpStatus.OK).body(authorDtos);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<AuthorEntity> authorEntity = authorService.findOne(id);
        return authorEntity.map(author -> ResponseEntity.status(HttpStatus.OK).body(authorMapper.mapTo(author)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id,
                                                      @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorEntity);
        return ResponseEntity.status(HttpStatus.OK).body(authorMapper.mapTo(savedAuthorEntity));
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id,
                                                   @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
            AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);
            return ResponseEntity.status(HttpStatus.OK).body(authorMapper.mapTo(updatedAuthor));
        }
    }
    @DeleteMapping("/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
