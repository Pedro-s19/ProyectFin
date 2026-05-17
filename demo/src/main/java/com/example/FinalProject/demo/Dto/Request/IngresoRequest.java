package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IngresoRequest {

    @NotNull
    @Positive
    private Double monto;
    private String descripcion;
    @NotNull
    private LocalDate fecha;
}
