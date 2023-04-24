package com.example.SpringSecurityAndSwagger.service.impl;

import com.example.SpringSecurityAndSwagger.dto.CreateUserDto;
import com.example.SpringSecurityAndSwagger.dto.UserDto;
import com.example.SpringSecurityAndSwagger.repository.UserRepository;
import com.example.SpringSecurityAndSwagger.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public UserDto signUpUser(CreateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto,User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }
}
