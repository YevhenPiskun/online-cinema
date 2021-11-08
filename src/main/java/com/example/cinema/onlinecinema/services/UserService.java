package com.example.cinema.onlinecinema.services;

import com.example.cinema.onlinecinema.exceptions.UserNotFoundException;
import com.example.cinema.onlinecinema.mappers.UserMapper;
import com.example.cinema.onlinecinema.dtos.UserDto;
import com.example.cinema.onlinecinema.repositories.UserRepository;
import com.example.cinema.onlinecinema.repositories.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper mapper;

    public User addUser(UserDto userDto) {
        User entity = mapper.dtoToEntity(userDto);
        userRepository.save(entity);
        return entity;
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            return mapper.entityToDto(optionalUser.get());
        } else {
            throw new UserNotFoundException(email + ": User Not Found");
        }
    }

    public List<UserDto> getAllUsers() {
        return mapper.entitiesToDto(userRepository.findAll());
    }

    public void deleteByEmail(String email) {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            userRepository.deleteUserByEmail(email);
        } else {
            throw new UserNotFoundException(email + ": User Not Found");
        }
    }

    public User updateUser(UserDto userDto) {
        Optional<User> optionalExistingUser = userRepository.getUserByEmail(userDto.getEmail());
        if (optionalExistingUser.isPresent()) {
            BeanUtils.copyProperties(userDto, optionalExistingUser.get(), "id");
            return userRepository.save(optionalExistingUser.get());
        } else {
            throw new UserNotFoundException(userDto.getEmail() + ": User Not Found");
        }
    }
}