package ru.hopenz.petLibrary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hopenz.petLibrary.data.dto.user.RequestUpdateProfile;
import ru.hopenz.petLibrary.data.entity.User;
import ru.hopenz.petLibrary.data.entity.enums.UserRole;
import ru.hopenz.petLibrary.repository.UserRepository;
import ru.hopenz.petLibrary.service.UserService;

import java.security.Principal;
import java.util.Map;

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

    @PostMapping("/profile/update")
    @ResponseBody
    public ResponseEntity<?> updateProfile(@ModelAttribute RequestUpdateProfile requestUpdateProfile,
                                           Principal principal) {
        try {
            User updatedUser = userService.updateUserProfile(principal.getName(), requestUpdateProfile);
            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "user", Map.of(
                            "name", updatedUser.getName(),
                            "surname", updatedUser.getSurname(),
                            "email", updatedUser.getEmail()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

}
