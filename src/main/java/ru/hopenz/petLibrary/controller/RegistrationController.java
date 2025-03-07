package ru.hopenz.petLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    //private final UserService userService;

//    public RegistrationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("user", new User());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String adduser(Model model, @ModelAttribute User user) {
//        if (!userService.addUser(user)) {
//            model.addAttribute("message", "User exists");
//            return "registration";
//        }
//        return "redirect:/login";
//    }

    @GetMapping("/")
    public String menu() {
        return "menu";
    }
}
