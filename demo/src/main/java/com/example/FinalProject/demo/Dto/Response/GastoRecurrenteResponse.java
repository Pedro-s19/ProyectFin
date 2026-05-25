package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data @Builder
public class GastoRecurrenteResponse {
    private UUID id;
    private String nombre;
    private Double monto;
    private int diaMes;
    private boolean activo;
    private String categoriaNombre;
    private UUID categoriaId;
}
