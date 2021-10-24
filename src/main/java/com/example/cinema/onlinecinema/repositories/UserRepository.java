package com.example.cinema.onlinecinema.repositories;

import com.example.cinema.onlinecinema.repositories.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    int deleteUserByEmail(String email);

    List<User> findAll();
}