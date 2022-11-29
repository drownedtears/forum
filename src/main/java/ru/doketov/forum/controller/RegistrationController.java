package ru.doketov.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.doketov.forum.service.UserService;
import ru.doketov.forum.model.User;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(ModelMap model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm,
                          BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "registration";
        }
        String result = userService.saveUser(userForm);
        if (result.equals("exists")) {
            model.addAttribute("usernameError", "Username already exists");
            return "registration";
        } else if (result.equals("small")) {
            model.addAttribute("usernameError", "Must be more than 2 symbols");
            return "registration";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
