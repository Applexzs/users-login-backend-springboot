package com.applexzs.backend.usersapp.repositories;

import com.applexzs.backend.usersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface IUserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> getUserByUsername(String username);
}
