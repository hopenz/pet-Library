package ru.hopenz.petLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hopenz.petLibrary.data.entity.User;
import ru.hopenz.petLibrary.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping
@Validated
public class ViewProfileController {

    private final UserService userService;

    public ViewProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();

        User user = userService.findByUsername(username);
        System.out.println(username);
        System.out.println(user.getName());
        System.out.println(user.getId());

        model.addAttribute("user", user);

        return "profile";
    }

}
