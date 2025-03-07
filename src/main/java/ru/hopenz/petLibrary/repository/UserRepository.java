package ru.hopenz.petLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hopenz.petLibrary.data.entity.Book;
import ru.hopenz.petLibrary.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
