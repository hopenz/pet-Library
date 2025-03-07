package ru.hopenz.petLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.hopenz.petLibrary.data.entity.User;
import ru.hopenz.petLibrary.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String adduser(Model model, @ModelAttribute User user) {
        if (!userService.addUser(user)) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String menu() {
        return "redirect:/view/books";
    }
}
