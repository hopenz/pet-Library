package ru.hopenz.petLibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hopenz.petLibrary.data.dto.RequestBookDto;
import ru.hopenz.petLibrary.data.dto.RequestFiltersForBooksDto;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
import ru.hopenz.petLibrary.data.entity.Book;
import ru.hopenz.petLibrary.data.mapper.BookMapper;
import ru.hopenz.petLibrary.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/api/book")
@Validated
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
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

    /**
     * Фильтрация книг по названию/автору
     */
    @Operation(summary = "Получить отфильтрованные книги", description = "Фильтрация по названию/автору")
    @PostMapping("/filter")
    public ResponseEntity<Page<ResponseBookDto>> getBooksWithFilters(
            @Parameter(name = "page", description = "Номер страницы", example = "0")
            @NotNull @PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Parameter(name = "size", description = "Размер страницы", example = "10")
            @NotNull @PositiveOrZero @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody @Validated RequestFiltersForBooksDto requestFiltersForBooksDto) {
        Page<ResponseBookDto> responseBookDtoPage =
                bookService.getBooksWithFilters(page, size, requestFiltersForBooksDto);

        return ResponseEntity.ok(responseBookDtoPage);
    }

    /**
     * Вывод существующих книг в html
     *
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/books")
    public String showBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<ResponseBookDto> bookPage = bookService.getBooks(page, size);

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", bookPage.getNumber());
        model.addAttribute("totalPages", bookPage.getTotalPages());
        return "menu";
    }

    @GetMapping("/books/search")
    public String searchBooks(@RequestParam(name = "query", required = false) String query,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              Model model) {
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<ResponseBookDto> booksPage;

        if (query == null || query.isBlank()) {
            booksPage = bookService.getBooks(page, pageSize);
        } else {
            Page<Book> rawBooksPage = bookService.searchBooks(query, pageable);
            booksPage = rawBooksPage.map(bookMapper::toResponseDto);
        }

        model.addAttribute("books", booksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("query", query);

        if (booksPage.isEmpty()) {
            model.addAttribute("message", "Книги не найдены");
        }

        return "menu";
    }

}
