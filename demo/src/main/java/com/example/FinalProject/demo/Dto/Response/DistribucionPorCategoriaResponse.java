package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DistribucionPorCategoriaResponse {

    private List<ItemCategoriaGasto> items;
    private String moneda;

    @Data @Builder
    public static class ItemCategoriaGasto {
        private String categoriaNombre;
        private Double total;
        private Double porcentaje;
    }
}
