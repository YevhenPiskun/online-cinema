package com.example.cinema.onlinecinema.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String exception) {
        super(exception);
    }
}
