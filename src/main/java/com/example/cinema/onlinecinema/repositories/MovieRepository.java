package com.example.cinema.onlinecinema.repositories;

import com.example.cinema.onlinecinema.repositories.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Optional<Movie> getMovieByName(String name);

    int deleteMovieByName(String name);

    List<Movie> findAll();
}