package ru.hopenz.petLibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.hopenz.petLibrary.data.dto.RequestBookDto;
import ru.hopenz.petLibrary.data.dto.RequestFiltersForBooksDto;
import ru.hopenz.petLibrary.data.dto.RequestGenreForBooksDto;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
import ru.hopenz.petLibrary.data.entity.Book;
import ru.hopenz.petLibrary.data.mapper.BookMapper;
import ru.hopenz.petLibrary.exception.EntityNotFoundException;
import ru.hopenz.petLibrary.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public ResponseBookDto saveBook(RequestBookDto requestBookDto) {
        Book newBook = new Book();

        newBook.setTitle(requestBookDto.title());
        newBook.setAuthor(requestBookDto.genre());
        newBook.setPublicationDate(requestBookDto.publicationDate());
        newBook.setGenre(requestBookDto.genre());

        Book bookDB = bookRepository.save(newBook);

        return new ResponseBookDto(bookDB.getId(), bookDB.getTitle(),
                bookDB.getAuthor(), bookDB.getPublicationDate(), bookDB.getGenre(), bookDB.getDescription());
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public ResponseBookDto updateBook(Long id, RequestBookDto requestBookDto) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Book", id));

        book.setTitle(requestBookDto.title());
        book.setAuthor(requestBookDto.author());
        book.setPublicationDate(requestBookDto.publicationDate());
        book.setGenre(requestBookDto.genre());

        bookRepository.save(book);

        return new ResponseBookDto(book.getId(), book.getTitle(),
                book.getAuthor(), book.getPublicationDate(), book.getGenre(), book.getDescription());
    }

    @Transactional
    public List<ResponseBookDto> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        List<ResponseBookDto> responseList = new ArrayList<>();
        for (int i = 0; i < allBooks.size(); i++) {
            Book bookDB = allBooks.get(i);
            responseList.add(new ResponseBookDto(bookDB.getId(), bookDB.getTitle(),
                    bookDB.getAuthor(), bookDB.getPublicationDate(), bookDB.getGenre(), bookDB.getDescription()));
        }

        return responseList;
    }

    public ResponseBookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("book", id));

        return new ResponseBookDto(book.getId(), book.getTitle(),
                book.getAuthor(), book.getPublicationDate(), book.getGenre(), book.getDescription());
    }


    public Page<ResponseBookDto> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.map(book -> new ResponseBookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationDate(),
                book.getGenre(),
                book.getDescription()
        ));
    }

    public Page<ResponseBookDto> getBooksWithFilters(Integer page, Integer size, RequestFiltersForBooksDto requestFiltersForBooksDto) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Book> bookSpecification = Specification.
                where(BookSpecification.hasTitle(requestFiltersForBooksDto.title())).
                and(BookSpecification.hasAuthor(requestFiltersForBooksDto.author()));

        Page<Book> books = bookRepository.findAll(bookSpecification, pageable);

        return books.map(bookMapper::toResponseDto);
    }

    // В сервисе
    public Page<Book> searchBooks(String query, String genre, Pageable pageable) {
        return bookRepository.findByTitleOrAuthorOrGenre(
                query != null ? query : "",
                genre != null ? genre : "",
                pageable
        );
    }

    public Page<ResponseBookDto> getBooksWithGenre(Integer page, Integer size, RequestGenreForBooksDto requestGenreForBooksDto) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Book> bookSpecification = Specification.
                where(BookSpecification.hasGenre(requestGenreForBooksDto.genre()));

        Page<Book> books = bookRepository.findAll(bookSpecification, pageable);

        return books.map(bookMapper::toResponseDto);
    }

    public List<String> getAllGenres() {
        return bookRepository.findAllGenre();
    }
}