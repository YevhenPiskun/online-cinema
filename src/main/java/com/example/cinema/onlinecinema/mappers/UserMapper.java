package com.example.cinema.onlinecinema.mappers;

import com.example.cinema.onlinecinema.dtos.UserDto;
import com.example.cinema.onlinecinema.repositories.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDto(User movie);

    User dtoToEntity(UserDto movieDto);

    List<UserDto> entitiesToDto(List<User> movies);

}