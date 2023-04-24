package ru.kata.spring.boot_security.demo.services;



import ru.kata.spring.boot_security.demo.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findOne(int id);
    void save(User user);
    void update(int id, User updateUser);
    void delete(int id);
    User getUserFromPrincipal(Principal principal);
}
