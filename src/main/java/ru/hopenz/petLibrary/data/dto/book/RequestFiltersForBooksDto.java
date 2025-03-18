package ru.hopenz.petLibrary.data.dto.book;

import java.io.Serializable;

public record RequestFiltersForBooksDto(String title, String author) implements Serializable {
}
