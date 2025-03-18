package ru.hopenz.petLibrary.data.dto.book;

import java.io.Serializable;
import java.time.LocalDate;

public record RequestBookDto(String title, String author,
                             LocalDate publicationDate, String genre) implements Serializable {
}
