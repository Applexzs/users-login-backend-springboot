package com.applexzs.backend.usersapp.services;

import com.applexzs.backend.usersapp.models.dto.UserDto;
import com.applexzs.backend.usersapp.models.dto.mapper.DtoMapperUser;
import com.applexzs.backend.usersapp.models.entities.Role;
import com.applexzs.backend.usersapp.models.entities.User;
import com.applexzs.backend.usersapp.models.request.UserRequest;
import com.applexzs.backend.usersapp.repositories.IUserRepository;
import com.applexzs.backend.usersapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = (List<User>) repository.findAll();
        return users
                .stream()
                .map(u -> DtoMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        Optional<User> o = repository.findById(id);
        if(o.isPresent()){
            return Optional.of(
                    DtoMapperUser.builder().setUser(o.orElseThrow()).build()
            );
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public UserDto save(User user) {
        String passwordBC = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordBC);
        Optional<Role> o = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if(o.isPresent()){
            roles.add(o.orElseThrow());
        }
        user.setRoles(roles);
        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }

    @Override
    @Transactional
    public Optional<UserDto> update(UserRequest user, Long id) {
        Optional<User> op = repository.findById(id);
        User userOptional = null;
        if (op.isPresent()) {
            User userDb = op.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            Optional.of(repository.save(userDb));
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
