package com.applexzs.backend.usersapp.services;

import com.applexzs.backend.usersapp.models.dto.UserDto;
import com.applexzs.backend.usersapp.models.entities.User;
import com.applexzs.backend.usersapp.models.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    UserDto save(User user);

    Optional<UserDto> update(UserRequest user, Long id);

    void remove(Long id);
}
