package ru.hopenz.petLibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hopenz.petLibrary.data.dto.RequestBookDto;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
import ru.hopenz.petLibrary.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Поиск книги по id", description = "Поиск книги по id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookDto> getBookById(@PathVariable Long id) {
        ResponseBookDto responseBookDto = bookService.getBookById(id);
        return ResponseEntity.ok(responseBookDto);
    }

    @Operation(summary = "Удаление книги", description = "Удаление книги")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Вывод всех книг", description = "Вывод всех книг")
    @GetMapping
    public ResponseEntity<List<ResponseBookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "Добавление новой книги", description = "Добавление новой книги")
    @PostMapping
    public ResponseEntity<RequestBookDto> createNewBook(@RequestBody RequestBookDto requestBookDto) {
        bookService.saveBook(requestBookDto);
        return ResponseEntity.ok(requestBookDto);
    }

    @Operation(summary = "Изменение данных о книге", description = "Изменение данных о книге")
    @PutMapping("/{id}")
    public ResponseEntity<RequestBookDto> updateBook(@PathVariable Long id, @RequestBody RequestBookDto requestBookDto) {
        bookService.updateBook(id, requestBookDto);
        return ResponseEntity.ok(requestBookDto);
    }
}
