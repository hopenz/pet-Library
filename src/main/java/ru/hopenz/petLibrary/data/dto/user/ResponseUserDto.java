package ru.hopenz.petLibrary.data.dto.user;

import ru.hopenz.petLibrary.data.entity.enums.UserRole;

import java.io.Serializable;

public record ResponseUserDto(Long id, String name, String surname,
                              String username, String email, UserRole role) implements Serializable {
}
