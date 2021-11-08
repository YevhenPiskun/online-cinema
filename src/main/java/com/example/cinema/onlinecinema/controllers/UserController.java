package com.example.cinema.onlinecinema.controllers;

import com.example.cinema.onlinecinema.dtos.UserDto;
import com.example.cinema.onlinecinema.repositories.models.User;
import com.example.cinema.onlinecinema.services.UserService;
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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public UserDto getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody final UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteUserByEmail(String email) {
        userService.deleteByEmail(email);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody final UserDto userDto) {
        return userService.updateUser(userDto);
    }
}