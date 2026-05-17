package com.example.FinalProject.demo.Dto.Request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SincronizacionRequest {
    private LocalDateTime ultimaSincronizacion;
    private List<GastoOfflineDto> gastosLocales;

    @Data
    public static class GastoOfflineDto {
        private UUID id;
        private Double cantidad;
        private String descripcion;
        private LocalDate fecha;
        private UUID categoriaId;
        private LocalDateTime fechaActualizacionLocal;
        private boolean eliminado;
    }
}
