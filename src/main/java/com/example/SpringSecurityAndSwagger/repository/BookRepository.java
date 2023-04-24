package com.example.SpringSecurityAndSwagger.repository;

import com.example.SpringSecurityAndSwagger.repository.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book,Long> {
    Optional<Book> findByAuthorEmail(String email);
}
