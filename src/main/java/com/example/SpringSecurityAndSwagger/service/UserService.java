package com.example.SpringSecurityAndSwagger.service;

import com.example.SpringSecurityAndSwagger.dto.AllUserInfoDto;
import com.example.SpringSecurityAndSwagger.dto.UserDto;

public interface UserService {
    UserDto getById(Long id);
    AllUserInfoDto getAllInfoByEmail(String email);
    void deleteUserById(Long id);
}
