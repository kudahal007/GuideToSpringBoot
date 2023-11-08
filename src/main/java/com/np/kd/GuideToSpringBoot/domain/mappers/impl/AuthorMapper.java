package com.np.kd.GuideToSpringBoot.domain.mappers.impl;

import com.np.kd.GuideToSpringBoot.domain.dto.AuthorDto;
import com.np.kd.GuideToSpringBoot.domain.entities.AuthorEntity;
import com.np.kd.GuideToSpringBoot.domain.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {
    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
