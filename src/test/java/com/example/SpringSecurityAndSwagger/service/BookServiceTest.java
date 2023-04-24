package com.example.SpringSecurityAndSwagger.service;

import com.example.SpringSecurityAndSwagger.dto.BookDto;
import com.example.SpringSecurityAndSwagger.exeption.BadRequestExeption;
import com.example.SpringSecurityAndSwagger.exeption.NotFoundExeption;
import com.example.SpringSecurityAndSwagger.repository.BookRepository;
import com.example.SpringSecurityAndSwagger.repository.entity.Book;
import com.example.SpringSecurityAndSwagger.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl(bookRepository,new ModelMapper());
    }

    @Test
    public void testCreateBook() {
        BookDto bookDto = BookDto.builder().title("TestTitle").description("TestDescription").authorEmail("test@gmail.com").build();
        Book book = Book.builder().title("TestTitle").description("TestDescription").authorEmail("test@gmail.com").build();
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookDto createdBook = bookService.createBook(bookDto);

        assertEquals(bookDto,createdBook);
        verify(bookRepository,times(1)).save(Mockito.any(Book.class));

    }

    @Test
    public void testGetById_Success() {
        BookDto bookDto = BookDto.builder().title("TestTitle").description("TestDescription").authorEmail("test@gmail.com").build();
        Book book = Book.builder().id(1L).title("TestTitle").description("TestDescription").authorEmail("test@gmail.com").build();
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        BookDto receivedBook = bookService.getById(1L);

        assertEquals(bookDto,receivedBook);
    }

    @Test
    public void testGetById_NotFound() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundExeption.class,() -> bookService.getById(id));
    }

    @Test
    public void testGetByAuthorEmail() {
        String email = "test@gmail.com";
        BookDto bookDto = BookDto.builder().title("TestTitle").description("TestDescription").authorEmail("test@gmail.com").build();
        Book book = Book.builder().id(1L).title("TestTitle").description("TestDescription").authorEmail("test@gmail.com").build();
        when(bookRepository.findByAuthorEmail(email)).thenReturn(Optional.of(book));

        BookDto receivedBook = bookService.getByAuthorEmail(email);

        assertEquals(bookDto,receivedBook);
    }

    @Test
    public void testGetByAuthorEmail_NotFound() {
        String email = "test@gmail.com";
        when(bookRepository.findByAuthorEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFoundExeption.class,() -> bookService.getByAuthorEmail(email));
    }

    @Test
    public void testGetAll() {
        List<Book> books = List.of(
                Book.builder().title("T1").description("D1").authorEmail("E1@gmail.com").build(),
                Book.builder().title("T2").description("D2").authorEmail("E2@gmail.com").build()
                );
        when(bookRepository.findAll()).thenReturn(books);

        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getTitle()).isEqualTo("T1");
        assertThat(books.get(0).getDescription()).isEqualTo("D1");
        assertThat(books.get(0).getAuthorEmail()).isEqualTo("E1@gmail.com");
        assertThat(books.get(1).getTitle()).isEqualTo("T2");
        assertThat(books.get(1).getDescription()).isEqualTo("D2");
        assertThat(books.get(1).getAuthorEmail()).isEqualTo("E2@gmail.com");
    }

    @Test
    public void testDeleteById() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteById(1L);

        verify(bookRepository,times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_InvalidId() {
        BadRequestExeption badRequestExeption = assertThrows(BadRequestExeption.class, () -> {
            bookService.deleteById(null);
        });

        assertThat(badRequestExeption.getMessage()).isEqualTo("Id null!");
        verify(bookRepository,never()).deleteById(null);
    }
}
