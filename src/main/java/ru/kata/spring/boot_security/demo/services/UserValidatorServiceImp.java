package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.Optional;

@Service
public class UserValidatorServiceImp implements UserValidatorService {

    private final UsersRepository usersRepository;
    @Autowired
    public UserValidatorServiceImp(UsersRepository usersRepository) {

        this.usersRepository = usersRepository;
    }


    @Override
    public Optional<User> loadUserByUsername(String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user;
    }
}
