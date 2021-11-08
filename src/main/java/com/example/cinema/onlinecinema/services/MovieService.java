package com.example.cinema.onlinecinema.services;

import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.dtos.Tariff;
import com.example.cinema.onlinecinema.dtos.UserDto;
import com.example.cinema.onlinecinema.exceptions.MovieNotFoundException;
import com.example.cinema.onlinecinema.mappers.MovieMapper;
import com.example.cinema.onlinecinema.repositories.MovieRepository;
import com.example.cinema.onlinecinema.repositories.models.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieMapper mapper;
    @Autowired
    UserService userService;

    public Movie addMovie(MovieDto movieDto) {
        Movie entity = mapper.dtoToEntity(movieDto);
        return movieRepository.save(entity);
    }

    public MovieDto getMovieByName(String name) {
        Optional<Movie> optionalMovieDto = movieRepository.getMovieByName(name);
        if (optionalMovieDto.isPresent()) {
            return mapper.entityToDto(optionalMovieDto.get());
        } else {
            throw new MovieNotFoundException(name + ": Movie Not Found");
        }
    }

    public List<MovieDto> getAllMovies() {
        return mapper.entitiesToDto(movieRepository.findAll());
    }

    public void deleteByName(String name) {
        Optional<Movie> optionalMovie = movieRepository.getMovieByName(name);
        if (optionalMovie.isPresent()) {
            movieRepository.deleteMovieByName(name);
        } else {
            throw new MovieNotFoundException(name + ": Movie Not Found");
        }
    }

    public Movie updateMovie(MovieDto movieDto) {
        Optional<Movie> optionalExistingMovie = movieRepository.getMovieByName(movieDto.getName());
        if (optionalExistingMovie.isPresent()) {
            BeanUtils.copyProperties(movieDto, optionalExistingMovie.get(), "id");
            return movieRepository.save(optionalExistingMovie.get());
        } else {
            throw new MovieNotFoundException(movieDto.getName() + ": Movie Not Found");
        }
    }

    public List<MovieDto> getMoviesByTariff(Tariff tariff) {
        return mapper.entitiesToDto(movieRepository.findAll()).stream()
                .filter(movieDto -> movieDto.getTariff().getPriority() >= tariff.getPriority())
                .collect(Collectors.toList());
    }

    public List<MovieDto> getAllAvailableMoviesForUser(String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return getMoviesByTariff(userDto.getTariff());
    }
}