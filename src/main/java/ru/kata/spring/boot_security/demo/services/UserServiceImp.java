package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;


import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {


    private final RoleService roleService;


    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;
    @Autowired
    public UserServiceImp(RoleService roleService, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }
    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }


    @Override
    public User findOne(int id) {
        Optional<User> foundUser= usersRepository.findById(id);
        return foundUser.orElse(null);
    }


    //Метод для получения юзера из его сессии после аутентификации
    @Override
    public User getUserFromPrincipal(Principal principal) {
        User user = usersRepository.findByUsername(principal.getName()).get();
        return findOne(user.getId());
    }



    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.findOne(1));
        usersRepository.save(user);
    }


    @Override
    @Transactional
    public void update(int id, User updateUser) {
        updateUser.setId(id);
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));

        //Добавлениеи к старым ролям
        User existingUser = findOne(id);
        Collection<Role> existingRoles = existingUser.getRoles();
        Collection<Role> newRole = updateUser.getRoles();
        if (!existingRoles.containsAll(newRole)) {
            existingRoles.addAll(newRole);
        }
        updateUser.setRoles(existingRoles);


        usersRepository.save(updateUser);
    }
    @Override
    @Transactional
    public void delete(int id) {
        User user = findOne(id);
        user.getAuthorities().clear();
        usersRepository.save(user);
        usersRepository.deleteById(id);
    }
}
