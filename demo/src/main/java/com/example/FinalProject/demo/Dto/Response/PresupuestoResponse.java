package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PresupuestoResponse {

    private UUID id;
    private UUID categoriaId;
    private String categoriaNombre;
    private int anio;
    private int mes;
    private Double limiteMonto;
    private Double gastadoMonto;
    private String moneda;
}
