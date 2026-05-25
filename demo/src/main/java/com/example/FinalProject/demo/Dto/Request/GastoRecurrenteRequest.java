package com.example.FinalProject.demo.Dto.Request;

import lombok.Data;
import java.util.UUID;

@Data
public class GastoRecurrenteRequest {
    private String nombre;
    private Double monto;
    private int diaMes;
    private UUID categoriaId;
}
