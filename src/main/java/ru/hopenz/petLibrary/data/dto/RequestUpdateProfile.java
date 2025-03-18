package ru.hopenz.petLibrary.data.dto;

import java.io.Serializable;

public record RequestUpdateProfile(String name, String surname, String email) implements Serializable {
}
