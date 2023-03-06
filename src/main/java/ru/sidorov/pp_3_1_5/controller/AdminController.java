package ru.sidorov.pp_3_1_5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sidorov.pp_3_1_5.model.User;
import ru.sidorov.pp_3_1_5.service.RoleService;
import ru.sidorov.pp_3_1_5.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "users";
    }
}
