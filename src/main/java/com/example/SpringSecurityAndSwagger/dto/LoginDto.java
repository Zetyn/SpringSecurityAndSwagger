package com.example.SpringSecurityAndSwagger.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    @Email
    private String email;
    private String password;
}
