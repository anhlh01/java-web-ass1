package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("isError", false);
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute("user") User user, Model model) {
        String errorMessage = null;
        if (!userService.isUsernameAvailable(user.getUserName())) {
            errorMessage = "Username is already in use";
            model.addAttribute("isError", false);
        } else {
            int row = userService.createUser(user);
            if (row <= 0) {
                errorMessage = "Create user is error";
                model.addAttribute("isError", false);
            } else {
                model.addAttribute("isError", true);
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        if (errorMessage != null) {
            return "signup";
        }
        return "login";
    }
}
