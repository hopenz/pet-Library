package ru.hopenz.petLibrary.data.dto;

import java.io.Serializable;

public record RequestFiltersForBooksDto(String title, String author) implements Serializable {
}
