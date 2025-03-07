package ru.hopenz.petLibrary.data.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record ResponseBookDto(Long id, String title, String author,
                              LocalDate publicationDate, String genre, String description) implements Serializable {
}