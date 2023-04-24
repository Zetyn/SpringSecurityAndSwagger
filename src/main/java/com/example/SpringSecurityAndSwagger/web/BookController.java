package com.example.SpringSecurityAndSwagger.web;

import com.example.SpringSecurityAndSwagger.dto.BookDto;
import com.example.SpringSecurityAndSwagger.service.BookService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@SecurityScheme(name = "jwt",type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    private BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "jwt")
    public BookDto createBook(@RequestBody @Validated BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @DeleteMapping("/book/{id}")
    @SecurityRequirement(name = "jwt")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}
