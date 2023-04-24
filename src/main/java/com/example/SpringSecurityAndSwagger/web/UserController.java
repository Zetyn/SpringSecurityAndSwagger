package com.example.SpringSecurityAndSwagger.web;

import com.example.SpringSecurityAndSwagger.dto.CreateUserDto;
import com.example.SpringSecurityAndSwagger.dto.UserDto;
import com.example.SpringSecurityAndSwagger.service.UserService;
import com.example.SpringSecurityAndSwagger.service.impl.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SignUpService signUpService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody @Validated CreateUserDto createUserDto) {
        return signUpService.signUpUser(createUserDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
