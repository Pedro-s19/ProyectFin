package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroRequest {
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 6)
    private String contrasena;
}
