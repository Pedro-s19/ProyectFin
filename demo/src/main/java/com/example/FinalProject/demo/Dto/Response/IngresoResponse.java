package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class IngresoResponse {

    private UUID id;
    private Double monto;
    private String descripcion;
    private LocalDate fecha;
    private String moneda;
}
