package ru.hopenz.petLibrary.data.entity.enums;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public enum UserRole {

    ROLE_ADMIN("Администратор"),

    ROLE_READER("Читатель");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
