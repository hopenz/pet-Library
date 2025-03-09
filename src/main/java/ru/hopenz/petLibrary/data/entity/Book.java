package ru.hopenz.petLibrary.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private User bookedUser;

    @Column(name = "is_booked")
    private boolean isBooked;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booked_before")
    private LocalDate bookedBefore;


    public Book(Long id, String title, String author, LocalDate publicationDate, String genre, String description,
                User bookedUser, boolean isBooked, LocalDate bookingDate,LocalDate bookedBefore) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.description = description;
        this.bookedUser = bookedUser;
        this.isBooked = isBooked;
        this.bookingDate = bookingDate;
        this.bookedBefore = bookedBefore;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getBookedUser() {
        return bookedUser;
    }

    public void setBookedUser(User bookedUser) {
        this.bookedUser = bookedUser;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getBookedBefore() {
        return bookedBefore;
    }

    public void setBookedBefore(LocalDate bookedBefore) {
        this.bookedBefore = bookedBefore;
    }
}
