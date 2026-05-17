package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class MetaAhorroResponse {

    private UUID id;
    private String nombre;
    private Double montoObjetivo;
    private Double montoActual;
    private Double porcentajeCompletado;
    private LocalDate fechaLimite;
    private String moneda;

}
