package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;


    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping()
    public String show(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserFromPrincipal(principal));
        return "users/show";
    }

}
