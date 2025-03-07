package ru.hopenz.petLibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hopenz.petLibrary.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Specification<Book> bookSpecification, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(concat('%', :query, '%')) OR LOWER(b.author) LIKE LOWER(concat('%', :query, '%'))")
    Page<Book> searchBooks(@Param("query") String query, Pageable pageable);
}