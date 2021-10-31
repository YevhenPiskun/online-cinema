package com.example.cinema.onlinecinema.integration;

import com.example.cinema.onlinecinema.core.AbstractDbTest;
import com.example.cinema.onlinecinema.dtos.Genre;
import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.dtos.Tariff;
import com.example.cinema.onlinecinema.repositories.MovieRepository;
import com.example.cinema.onlinecinema.services.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieServiceIntegrationDbTests extends AbstractDbTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void whenOnlyOneProductWithMandatoryFieldsIsProvided_thenOneProductIsCreated() {

        String movieName = "Titanic";

        MovieDto movieDto = new MovieDto();
        movieDto.setName(movieName);
        movieDto.setGenre(Genre.DRAMA);
        movieDto.setTariff(Tariff.MEDIUM);

        movieService.addMovie(movieDto);

        MovieDto actualMovie = movieService.getMovieByName(movieName);

        assertEquals(Genre.DRAMA, actualMovie.getGenre(), "Wrong movie genre");
        assertEquals(movieName, actualMovie.getName(), "Wrong movie name");
        assertEquals(Tariff.MEDIUM, actualMovie.getTariff(), "Wrong movie tariff");
    }

    @Test
    void whenThreeProductsIsProvided_thenTreeProductIsReturned() {

        MovieDto movieDto1 = new MovieDto();
        movieDto1.setName("Titanic");
        movieDto1.setGenre(Genre.DRAMA);
        movieDto1.setRating(7.8);
        movieDto1.setYear(1997);
        movieDto1.setTariff(Tariff.BASE);

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setName("The Shawshank Redemption");
        movieDto2.setGenre(Genre.DRAMA);
        movieDto2.setRating(9.3);
        movieDto2.setYear(1994);
        movieDto2.setTariff(Tariff.MEDIUM);

        MovieDto movieDto3 = new MovieDto();
        movieDto3.setName("Once Upon a Time... in Hollywood");
        movieDto3.setGenre(Genre.DRAMA);
        movieDto3.setRating(7.6);
        movieDto3.setYear(2019);
        movieDto3.setTariff(Tariff.PREMIUM);

        movieService.addMovie(movieDto1);
        movieService.addMovie(movieDto2);
        movieService.addMovie(movieDto3);

        List<MovieDto> actualMovies = movieService.getAllMovies();

        List<String> movieNames = actualMovies.stream().map(MovieDto::getName)
                .collect(Collectors.toList());

        assertEquals(3, actualMovies.size());
        assertThat(movieNames, Matchers.containsInAnyOrder("Titanic", "The Shawshank Redemption", "Once Upon a Time... in Hollywood"));
    }
}