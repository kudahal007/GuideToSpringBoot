package com.np.kd.GuideToSpringBoot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto {
    private String isbn;
    private String title;
    private AuthorDto authorDto;
}
