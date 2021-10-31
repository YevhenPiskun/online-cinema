package com.example.cinema.onlinecinema.core;

import com.example.cinema.onlinecinema.dtos.Genre;
import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.dtos.Tariff;
import com.example.cinema.onlinecinema.dtos.UserDto;
import com.example.cinema.onlinecinema.repositories.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {

    public static Movie createMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Titanic");
        movie.setGenre(Genre.DRAMA);
        movie.setRating(7.8);
        movie.setYear(1998);
        movie.setTariff(Tariff.BASE);

        return movie;
    }

    public static MovieDto createMovieDto() {
        MovieDto movieDto = new MovieDto();
        movieDto.setName("Titanic");
        movieDto.setGenre(Genre.DRAMA);
        movieDto.setRating(7.8);
        movieDto.setYear(1997);
        movieDto.setTariff(Tariff.BASE);

        return movieDto;
    }

    public static List<Movie> createMovieList() {

        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setName("Titanic");
        movie1.setGenre(Genre.DRAMA);
        movie1.setRating(7.8);
        movie1.setYear(1997);
        movie1.setTariff(Tariff.BASE);

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setName("The Shawshank Redemption");
        movie2.setGenre(Genre.DRAMA);
        movie2.setRating(9.3);
        movie2.setYear(1994);
        movie2.setTariff(Tariff.MEDIUM);

        Movie movie3 = new Movie();
        movie3.setId(3L);
        movie3.setName("Once Upon a Time... in Hollywood");
        movie3.setGenre(Genre.DRAMA);
        movie3.setRating(7.6);
        movie3.setYear(2019);
        movie3.setTariff(Tariff.PREMIUM);

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

        return movies;
    }

    public static List<MovieDto> createMovieDtoList() {

        List<MovieDto> movieDtos = new ArrayList<>();

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

        movieDtos.add(movieDto1);
        movieDtos.add(movieDto2);
        movieDtos.add(movieDto3);

        return movieDtos;
    }

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setName("Elon Musk");
        userDto.setEmail("elon_musk@gmail.com");
        userDto.setTariff(Tariff.MEDIUM);

        return userDto;
    }
}