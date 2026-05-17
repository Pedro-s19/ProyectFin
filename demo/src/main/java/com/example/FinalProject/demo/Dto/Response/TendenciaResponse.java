package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TendenciaResponse {

    private List<String> etiquetas;
    private List<Double> valores;
    private String moneda;
}
