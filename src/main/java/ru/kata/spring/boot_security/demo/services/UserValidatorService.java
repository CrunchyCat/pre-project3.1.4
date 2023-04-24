package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.Optional;

public interface UserValidatorService {
    Optional<User> loadUserByUsername(String username);
}
