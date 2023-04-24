package com.example.SpringSecurityAndSwagger.service;

import com.example.SpringSecurityAndSwagger.dto.AllUserInfoDto;
import com.example.SpringSecurityAndSwagger.dto.UserDto;
import com.example.SpringSecurityAndSwagger.exeption.BadRequestExeption;
import com.example.SpringSecurityAndSwagger.exeption.NotFoundExeption;
import com.example.SpringSecurityAndSwagger.repository.UserRepository;
import com.example.SpringSecurityAndSwagger.repository.entity.User;
import com.example.SpringSecurityAndSwagger.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository,new ModelMapper());
    }

    @Test
    public void testGetById_Success() {
        UserDto userDto = UserDto.builder().firstName("F1").lastName("L1").email("E1@gmail.com").build();
        User user = User.builder().firstName("F1").lastName("L1").email("E1@gmail.com").password("P1").build();
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        UserDto receivedUser = userService.getById(1L);

        assertEquals(userDto,receivedUser);
    }

    @Test
    public void testGetById_NotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundExeption.class,() -> userService.getById(id));
    }

    @Test
    public void testGetAllInfoByEmail_Success() {
        AllUserInfoDto userDto = AllUserInfoDto.builder().firstName("F1").lastName("L1").email("E1@gmail.com").password("P1").build();
        User user = User.builder().firstName("F1").lastName("L1").email("E1@gmail.com").password("P1").build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        AllUserInfoDto receivedUser = userService.getAllInfoByEmail("E1@gmail.com");

        assertEquals(userDto,receivedUser);
    }

    @Test
    public void testGetAllInfoByEmail_NotFound() {
        String email = "m@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFoundExeption.class,() -> userService.getAllInfoByEmail(email));
    }

    @Test
    public void testDeleteById() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUserById(1L);

        verify(userRepository,times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_InvalidId() {
        BadRequestExeption badRequestExeption = assertThrows(BadRequestExeption.class, () -> {
            userService.deleteUserById(null);
        });

        assertThat(badRequestExeption.getMessage()).isEqualTo("Id null!");
        verify(userRepository,never()).deleteById(null);
    }
}
