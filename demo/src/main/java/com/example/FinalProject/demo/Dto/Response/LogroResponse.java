package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data @Builder
public class LogroResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private String icono;
    private boolean desbloqueado;
    private int puntos;
}
