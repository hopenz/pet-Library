package ru.hopenz.petLibrary.service;

import org.springframework.data.jpa.domain.Specification;
import ru.hopenz.petLibrary.data.entity.Book;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) return null;
            return criteriaBuilder.like(root.get("title"), title + "%");
        };
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) return null;
            return criteriaBuilder.like(root.get("author"), author + "%");
        };
    }

}
