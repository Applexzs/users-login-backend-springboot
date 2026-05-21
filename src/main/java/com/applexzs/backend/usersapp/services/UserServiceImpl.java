package com.applexzs.backend.usersapp.services;

import com.applexzs.backend.usersapp.models.entities.User;
import com.applexzs.backend.usersapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
        Optional<User> op = this.findById(id);
        if (op.isPresent()) {
            User userDb = op.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return Optional.of(this.save(userDb));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
