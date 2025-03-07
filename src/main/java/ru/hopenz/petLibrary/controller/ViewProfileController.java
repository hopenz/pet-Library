package ru.hopenz.petLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@Validated
public class ViewProfileController {

    @GetMapping
    public String profilePage() {
        return "profile";
    }
}
