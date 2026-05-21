package com.applexzs.backend.usersapp.services;

import com.applexzs.backend.usersapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void remove(Long id);
}
