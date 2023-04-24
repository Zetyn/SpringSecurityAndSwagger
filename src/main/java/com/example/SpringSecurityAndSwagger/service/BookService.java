package com.example.SpringSecurityAndSwagger.service;

import com.example.SpringSecurityAndSwagger.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    List<BookDto> getAll();
    BookDto getById(Long id);
    BookDto getByAuthorEmail(String email);
    void deleteById(Long id);
}
