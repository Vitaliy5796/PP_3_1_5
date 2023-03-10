package ru.sidorov.pp_3_1_5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sidorov.pp_3_1_5.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user";
    }
}
