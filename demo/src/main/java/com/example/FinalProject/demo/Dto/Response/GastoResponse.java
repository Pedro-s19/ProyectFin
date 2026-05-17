package com.example.FinalProject.demo.Dto.Response;

import com.example.FinalProject.demo.Model.Categoria;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class GastoResponse {
    private UUID id;
    private Double monto;
    private String descripcion;
    private LocalDate fecha;
    private String moneda;
    private CategoriaResponse categoria;
}
