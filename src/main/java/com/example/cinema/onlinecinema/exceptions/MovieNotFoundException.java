package com.example.cinema.onlinecinema.exceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String exception) {
        super(exception);
    }
}