package com.np.kd.GuideToSpringBoot;

import com.np.kd.GuideToSpringBoot.domain.dto.AuthorDto;
import com.np.kd.GuideToSpringBoot.domain.dto.BookDto;
import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import com.np.kd.GuideToSpringBoot.domain.entities.BookEntity;

import java.awt.print.Book;

public class TestDataUtils {

    private TestDataUtils() {
    }

    public static AuthorEntity createTestAuthorEntityA() {
        return AuthorEntity.builder()
                .id(1L)
                .age(80)
                .name("Abigail Rose")
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static AuthorDto createTestAuthorDtoB() {
        return AuthorDto.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }
    public static BookEntity createTestBookEntityA(final AuthorEntity authorEntity){
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookDto createTestBookDtoA(final AuthorDto authorDto){
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorDto(authorDto)
                .build();
    }
}
