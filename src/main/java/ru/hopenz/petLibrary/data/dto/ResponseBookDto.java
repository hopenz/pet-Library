package ru.hopenz.petLibrary.data.dto;

import ru.hopenz.petLibrary.data.entity.User;

import java.io.Serializable;
import java.time.LocalDate;

public record ResponseBookDto(Long id, String title, String author,
                              LocalDate publicationDate, String genre, String description,
                              boolean isBooked, LocalDate bookingDate,
                              LocalDate bookedBefore) implements Serializable {
}