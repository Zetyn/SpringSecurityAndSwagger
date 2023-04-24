package com.example.SpringSecurityAndSwagger.service.impl;

import com.example.SpringSecurityAndSwagger.dto.BookDto;
import com.example.SpringSecurityAndSwagger.exeption.BadRequestExeption;
import com.example.SpringSecurityAndSwagger.exeption.NotFoundExeption;
import com.example.SpringSecurityAndSwagger.repository.BookRepository;
import com.example.SpringSecurityAndSwagger.repository.entity.Book;
import com.example.SpringSecurityAndSwagger.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookRepository.save(modelMapper.map(bookDto, Book.class));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public List<BookDto> getAll() {
        var books = (List<Book>) bookRepository.findAll();
        return books.stream()
                .map(book -> modelMapper.map(book,BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundExeption("Book with this id not found!"));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookDto getByAuthorEmail(String email) {
        Book book = bookRepository.findByAuthorEmail(email).orElseThrow(() -> new NotFoundExeption("Book with this author email not found!"));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            bookRepository.deleteById(id);
        } else throw new BadRequestExeption("Id null!");
    }
}
