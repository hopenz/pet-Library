package ru.hopenz.petLibrary.data.dto.user;

import java.io.Serializable;

public record RequestUpdateProfile(String name, String surname, String email) implements Serializable {
}
