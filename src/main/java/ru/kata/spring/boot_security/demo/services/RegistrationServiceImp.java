package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

@Service
public class RegistrationServiceImp implements RegistrationService {

    private final RoleService roleService;

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImp(RoleService roleService,
                                  UsersRepository usersRepository,
                                  PasswordEncoder passwordEncoder) {
        this.roleService = roleService;

        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setRoles(roleService.findOne(1));
        user.setPassword(encodedPassword);
        usersRepository.save(user);
    }
}
