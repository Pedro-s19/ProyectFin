package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SincronizacionResponse {

    private List<GastoResponse> gastosNuevosOActualizados;
    private LocalDateTime servidorTimestamp;
}
