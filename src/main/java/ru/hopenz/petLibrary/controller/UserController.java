package ru.hopenz.petLibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hopenz.petLibrary.data.dto.user.RequestUpdateProfile;
import ru.hopenz.petLibrary.data.dto.user.ResponseUserDto;
import ru.hopenz.petLibrary.service.UserService;

@Controller
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Поиск пользователя по id", description = "Поиск пользователя по id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id) {
        ResponseUserDto responseUserDto = userService.getUserById(id);
        return ResponseEntity.ok(responseUserDto);
    }

    @Operation(summary = "Удаление пользователя", description = "Удаление пользователя")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Изменение данных о пользователе", description = "Изменение данных о пользователе")
    @PutMapping("/{id}")
    public ResponseEntity<RequestUpdateProfile> updateUser(@PathVariable Long id, @RequestBody RequestUpdateProfile requestUpdateProfile) {
        userService.updateUser(id, requestUpdateProfile);
        return ResponseEntity.ok(requestUpdateProfile);
    }
}
