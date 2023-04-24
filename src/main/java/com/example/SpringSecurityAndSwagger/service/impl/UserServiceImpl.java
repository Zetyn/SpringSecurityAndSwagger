package com.example.SpringSecurityAndSwagger.service.impl;

import com.example.SpringSecurityAndSwagger.dto.AllUserInfoDto;
import com.example.SpringSecurityAndSwagger.exeption.BadRequestExeption;
import com.example.SpringSecurityAndSwagger.exeption.NotFoundExeption;
import com.example.SpringSecurityAndSwagger.repository.UserRepository;
import com.example.SpringSecurityAndSwagger.repository.entity.User;
import org.modelmapper.ModelMapper;
import com.example.SpringSecurityAndSwagger.dto.CreateUserDto;
import com.example.SpringSecurityAndSwagger.dto.UserDto;
import com.example.SpringSecurityAndSwagger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExeption("User with this id not found!"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public AllUserInfoDto getAllInfoByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundExeption("User with this email not found!"));
        return modelMapper.map(user, AllUserInfoDto.class);
    }

    @Override
    public void deleteUserById(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
        } else throw new BadRequestExeption("Id null!");
    }
}
