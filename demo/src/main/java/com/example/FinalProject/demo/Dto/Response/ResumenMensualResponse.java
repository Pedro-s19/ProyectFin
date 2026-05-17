package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumenMensualResponse {
    private Double totalGastos;
    private Double totalIngresos;
    private Double balance;
    private Double presupuestoRestante;
    private Double porcentajePresupuestoUsado;
    private String moneda;
}
