package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class PresupuestoRequest {

    @NotNull
    private UUID categoriaId;
    private int anio;
    private int mes;
    @NotNull @Positive
    private Double limiteMonto;
}
