package ru.hopenz.petLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hopenz.petLibrary.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}