package com.example.SpringSecurityAndSwagger.repository;

import com.example.SpringSecurityAndSwagger.repository.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
