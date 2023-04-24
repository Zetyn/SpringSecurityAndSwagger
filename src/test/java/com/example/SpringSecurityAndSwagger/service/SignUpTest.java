package com.example.SpringSecurityAndSwagger.service;

import com.example.SpringSecurityAndSwagger.dto.CreateUserDto;
import com.example.SpringSecurityAndSwagger.dto.UserDto;
import com.example.SpringSecurityAndSwagger.repository.UserRepository;
import com.example.SpringSecurityAndSwagger.repository.entity.User;
import com.example.SpringSecurityAndSwagger.service.impl.SignUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SignUpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignUpService signUpService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUpUser_Success() {
        CreateUserDto createUserDto = CreateUserDto.builder().email("email@gmail.com").password("password").build();
        User user = User.builder().email("email@gmail.com").password("password").build();
        User savedUser = User.builder().id(1L).email("email@gmail.com").password("encoded_password").build();
        UserDto expectedUserDto = UserDto.builder().email("email@gmail.com").build();

        when(modelMapper.map(createUserDto, User.class)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserDto.class)).thenReturn(expectedUserDto);

        UserDto actualUserDto = signUpService.signUpUser(createUserDto);

        assertThat(actualUserDto).isEqualTo(expectedUserDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSignUpUser_Exception() {
        CreateUserDto createUserDto = CreateUserDto.builder().email("email@gmail.com").password("password").build();
        User user = User.builder().email("email@gmail.com").password("password").build();

        when(modelMapper.map(createUserDto, User.class)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            signUpService.signUpUser(createUserDto);
        });
    }
}
