package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoriaResponse {
    private UUID id;
    private String nombre;
    private String icono;
    private boolean esPorDefecto;
}
