package com.example.cinema.onlinecinema.unit;

import com.example.cinema.onlinecinema.core.AbstractUnitTest;
import com.example.cinema.onlinecinema.core.DataFactory;
import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.dtos.Tariff;
import com.example.cinema.onlinecinema.dtos.UserDto;
import com.example.cinema.onlinecinema.exceptions.MovieNotFoundException;
import com.example.cinema.onlinecinema.repositories.MovieRepository;
import com.example.cinema.onlinecinema.repositories.models.Movie;
import com.example.cinema.onlinecinema.services.MovieService;
import com.example.cinema.onlinecinema.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieServiceUnitTests extends AbstractUnitTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @MockBean
    private MovieRepository movieRepository;

    private final String MOVIE_NAME = "Titanic";

    @BeforeEach
    void setUp() {
        Mockito.reset(movieRepository);
        Mockito.reset(userService);
    }

    @Test
    void whenGetMovieByNameIsAbsentInTheRepository_thenMovieNotFoundExceptionExpected() {

        Mockito.when(movieRepository.getMovieByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovieByName(MOVIE_NAME));
    }

    @Test
    void whenGetMovieByNameIsPresentInTheRepository_thenMovieExpected() {

        Movie movie = DataFactory.createMovie();

        Mockito.when(movieRepository.getMovieByName(MOVIE_NAME))
                .thenReturn(Optional.of(movie));

        MovieDto movieDto = movieService.getMovieByName(MOVIE_NAME);

        assertEquals(movie.getName(), movieDto.getName());
        assertEquals(movie.getTariff(), movieDto.getTariff());
        assertEquals(movie.getGenre(), movieDto.getGenre());
        assertEquals(movie.getRating(), movieDto.getRating());
        assertEquals(movie.getYear(), movieDto.getYear());
    }

    @Test
    void whenUserDeleteMovieByNameAndMovieIsAbsentInTheRepository_thenMovieNotFoundExceptionExpected() {

        Mockito.when(movieRepository.getMovieByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.deleteByName(MOVIE_NAME));
    }

    @Test
    void whenUserTryToUpdateNotExistedMovie_thenMovieNotFoundExceptionExpected() {

        MovieDto movieDto = DataFactory.createMovieDto();

        Mockito.when(movieRepository.getMovieByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(movieDto));
    }

    @Test
    void whenUserGetMoviesByTariff_thenListOfMoviesExpected() {

        List<Movie> movies = DataFactory.createMovieList();

        Mockito.when(movieRepository.findAll())
                .thenReturn(movies);

        List<MovieDto> movieDtos = movieService.getMoviesByTariff(Tariff.MEDIUM);

        assertEquals(2, movieDtos.size());
        movieDtos.forEach(movieDto -> assertNotEquals("Once Upon a Time... in Hollywood", movieDto.getName()));
        assertEquals("Titanic", movieDtos.get(0).getName());
        assertEquals("The Shawshank Redemption", movieDtos.get(1).getName());
        assertEquals(Tariff.BASE, movieDtos.get(0).getTariff());
        assertEquals(Tariff.MEDIUM, movieDtos.get(1).getTariff());
    }

    @Test
    void whenUserGetMoviesAvailableForUser_thenListOfMoviesExpected() {

        String email = "elon_musk@gmail.com";

        UserDto userDto = DataFactory.createUserDto();
        List<Movie> movies = DataFactory.createMovieList();

        Mockito.when(movieRepository.findAll())
                .thenReturn(movies);
        Mockito.when(userService.getUserByEmail(email))
                .thenReturn(userDto);

        List<MovieDto> movieDtos = movieService.getAllAvailableMoviesForUser(email);

        assertEquals(2, movieDtos.size());
        movieDtos.forEach(movieDto -> assertNotEquals("Once Upon a Time... in Hollywood", movieDto.getName()));
        assertEquals("Titanic", movieDtos.get(0).getName());
        assertEquals("The Shawshank Redemption", movieDtos.get(1).getName());
        assertEquals(Tariff.BASE, movieDtos.get(0).getTariff());
        assertEquals(Tariff.MEDIUM, movieDtos.get(1).getTariff());
    }
}