package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService{

    private final RolesRepository rolesRepository;
    @Autowired
    public RoleServiceImp(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Role findOne(int id) {
        Optional<Role> role  = rolesRepository.findById(id);
        return role.orElse(null);
    }

}
