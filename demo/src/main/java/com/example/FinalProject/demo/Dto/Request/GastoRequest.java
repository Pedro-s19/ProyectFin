package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class GastoRequest {

    @NotNull@Positive
    private Double monto;
    private String descripcion;
    @NotNull
    private LocalDate fecha;
    @NotNull
    private UUID categoriaId;
}
