package com.example.cinema.onlinecinema.mappers;

import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.repositories.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto entityToDto(Movie movie);

    Movie dtoToEntity(MovieDto movieDto);

    List<MovieDto> entitiesToDto(List<Movie> movies);

}