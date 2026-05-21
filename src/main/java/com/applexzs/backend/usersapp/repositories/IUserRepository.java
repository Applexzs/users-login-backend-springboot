package com.applexzs.backend.usersapp.repositories;

import com.applexzs.backend.usersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface IUserRepository extends CrudRepository<User, Long> {
}
