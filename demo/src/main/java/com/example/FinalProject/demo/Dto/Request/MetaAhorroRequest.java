package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MetaAhorroRequest {
    @NotBlank
    private String nombre;
    @NotNull @Positive
    private Double montoObjetivo;
    private LocalDate fechaLimite;
}
