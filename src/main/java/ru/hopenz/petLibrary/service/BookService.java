package ru.hopenz.petLibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hopenz.petLibrary.data.dto.RequestBookDto;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
import ru.hopenz.petLibrary.data.entity.Book;
import ru.hopenz.petLibrary.exception.EntityNotFoundException;
import ru.hopenz.petLibrary.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
                bookDB.getAuthor(), bookDB.getPublicationDate(), bookDB.getGenre());
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
                book.getAuthor(), book.getPublicationDate(), book.getGenre());
    }

    @Transactional
    public List<ResponseBookDto> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        List<ResponseBookDto> responseList = new ArrayList<>();
        for (int i = 0; i < allBooks.size(); i++) {
            Book bookDB = allBooks.get(i);
            responseList.add(new ResponseBookDto(bookDB.getId(), bookDB.getTitle(),
                    bookDB.getAuthor(), bookDB.getPublicationDate(), bookDB.getGenre()));
        }

        return responseList;
    }

    public ResponseBookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("book", id));

        return new ResponseBookDto(book.getId(), book.getTitle(),
                book.getAuthor(), book.getPublicationDate(), book.getGenre());
    }


}