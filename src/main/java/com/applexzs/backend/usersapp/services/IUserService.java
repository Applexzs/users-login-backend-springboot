package com.applexzs.backend.usersapp.services;

import com.applexzs.backend.usersapp.models.entities.User;
import com.applexzs.backend.usersapp.models.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update(UserRequest user, Long id);

    void remove(Long id);
}
