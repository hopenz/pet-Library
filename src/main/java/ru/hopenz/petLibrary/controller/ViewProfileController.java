package ru.hopenz.petLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hopenz.petLibrary.data.entity.User;
import ru.hopenz.petLibrary.data.entity.enums.UserRole;
import ru.hopenz.petLibrary.repository.UserRepository;
import ru.hopenz.petLibrary.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping
@Validated
public class ViewProfileController {

    private final UserService userService;
    private final UserRepository userRepository;

    public ViewProfileController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();

        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        User user1 = userRepository.findByUsername(username);
        UserRole role = user1.getRole();
        model.addAttribute("userRole", role.name());

        return "profile";
    }

}
