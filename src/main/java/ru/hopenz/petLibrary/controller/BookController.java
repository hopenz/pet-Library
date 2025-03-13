package ru.hopenz.petLibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hopenz.petLibrary.data.dto.RequestBookDto;
import ru.hopenz.petLibrary.data.dto.RequestFiltersForBooksDto;
import ru.hopenz.petLibrary.data.dto.RequestGenreForBooksDto;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
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
     * Фильтрация книг по жанру
     *
     * @param page
     * @param size
     * @param requestGenreForBooksDto
     * @return
     */
    @Operation(summary = "Получить отфильтрованные книги", description = "Фильтрация по жанру")
    @PostMapping("/genre")
    public ResponseEntity<Page<ResponseBookDto>> getBooksWithGenre(
            @Parameter(name = "page", description = "Номер страницы", example = "0")
            @NotNull @PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Parameter(name = "size", description = "Размер страницы", example = "10")
            @NotNull @PositiveOrZero @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody @Validated RequestGenreForBooksDto requestGenreForBooksDto) {
        Page<ResponseBookDto> responseBookDtoPage =
                bookService.getBooksWithGenre(page, size, requestGenreForBooksDto);

        return ResponseEntity.ok(responseBookDtoPage);
    }

    /**
     * Бронирование книги на n-ое количество дней
     */
    @Operation(summary = "Бронирование книги", description = "Бронирование книги пользователем")
    @PutMapping("/booking")
    public ResponseEntity<ResponseBookDto> bookingBook(
            @Parameter(name = "userId", description = "id пользователя", example = "1")
            @NotNull @Positive @RequestParam(value = "userId") Long userId,
            @Parameter(name = "bookId", description = "id книги", example = "1")
            @NotNull @Positive @RequestParam(value = "bookId") Long bookId,
            @Parameter(name = "dayOfBooking", description = "n-ое количество дней бронирования книги", example = "1")
            @NotNull @Positive @RequestParam(value = "dayOfBooking") Integer dayOfBooking) {
        return ResponseEntity.ok(bookService.bookingBook(userId, bookId, dayOfBooking));
    }

    /**
     * Снятие бронирования с книги
     */
    @Operation(summary = "Снятие бронирования с книги", description = "Снятие бронирования с книги")
    @PutMapping("/unlock")
    public ResponseEntity<ResponseBookDto> bookingBook(
            @Parameter(name = "userId", description = "id пользователя", example = "1")
            @NotNull @Positive @RequestParam(value = "userId") Long userId,
            @Parameter(name = "bookId", description = "id книги", example = "1")
            @NotNull @Positive @RequestParam(value = "bookId") Long bookId) {
        return ResponseEntity.ok(bookService.unlock(userId, bookId));
    }

}
