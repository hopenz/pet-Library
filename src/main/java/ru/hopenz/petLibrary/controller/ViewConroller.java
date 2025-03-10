package ru.hopenz.petLibrary.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
import ru.hopenz.petLibrary.data.entity.User;
import ru.hopenz.petLibrary.data.entity.enums.UserRole;
import ru.hopenz.petLibrary.repository.UserRepository;
import ru.hopenz.petLibrary.service.BookService;
import ru.hopenz.petLibrary.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/view")
@Validated
public class ViewConroller {

    private final BookService bookService;
    private final UserRepository userRepository;

    public ViewConroller(BookService bookService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userRepository = userRepository;
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
            @RequestParam(defaultValue = "9") int size,
            Model model
    ) {
        Page<ResponseBookDto> bookPage = bookService.getBooks(page, size);

        addGenresToModel(model);
        addUserRoleToModel(model); // Добавляем роль пользователя в модель

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", bookPage.getNumber());
        model.addAttribute("totalPages", bookPage.getTotalPages());
        return "menu";
    }

    /**
     * Поиск книг в html
     *
     * @param query
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/books/search")
    public String searchBooks(@RequestParam(name = "query", required = false) String query,
                              @RequestParam(name = "genre", required = false) String genre,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              Model model) {

        int pageSize = 9;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<ResponseBookDto> booksPage;

        // Логика поиска
        if ((query == null || query.isBlank()) && (genre == null || genre.isBlank())) {
            // Если оба параметра пустые - показываем все книги
            booksPage = bookService.getBooks(page, pageSize);
        } else {
            // Если есть хотя бы один параметр - выполняем поиск
            booksPage = bookService.searchBooks(query, genre, pageable);
        }

        addGenresToModel(model);
        addUserRoleToModel(model); // Добавляем роль пользователя в модель

        model.addAttribute("books", booksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("genre", genre);

        return "menu";
    }

    private void addGenresToModel(Model model) {
        List<String> genres = bookService.getAllGenres();
        model.addAttribute("genres", genres);
    }

    private void addUserRoleToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            UserRole role = user.getRole();
            model.addAttribute("userRole", role.name());
        }
    }
}