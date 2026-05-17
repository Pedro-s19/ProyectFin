package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequest {
    @NotBlank
    private String nombre;
    private String icono;
}
