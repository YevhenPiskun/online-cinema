package com.example.cinema.onlinecinema.controllers;

import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.dtos.Tariff;
import com.example.cinema.onlinecinema.exceptions.MovieNotFoundException;
import com.example.cinema.onlinecinema.repositories.models.Movie;
import com.example.cinema.onlinecinema.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public MovieDto getMovieByName(String name) {
        return movieService.getMovieByName(name);
    }

    @GetMapping("/all")
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/tariff")
    public List<MovieDto> getMoviesByTariff(Tariff tariff) {
        return movieService.getMoviesByTariff(tariff);
    }

    @GetMapping("/user")
    public List<MovieDto> getAllAvailableMoviesForUser(String email) {
        return movieService.getAllAvailableMoviesForUser(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@Valid @RequestBody final MovieDto movieDto) {
        return movieService.addMovie(movieDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteByMovieName(String name) {
        movieService.deleteByName(name);
    }

    @PutMapping
    public Movie updateMovie(@Valid @RequestBody final MovieDto movieDto) throws MovieNotFoundException {
        return movieService.updateMovie(movieDto);
    }
}