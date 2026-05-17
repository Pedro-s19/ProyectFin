package com.example.FinalProject.demo.Mapper;

import com.example.FinalProject.demo.Dto.Request.MetaAhorroRequest;
import com.example.FinalProject.demo.Dto.Response.MetaAhorroResponse;
import com.example.FinalProject.demo.Model.MetaAhorro;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MetaAhorroMapper {

    public MetaAhorro toEntity(MetaAhorroRequest request, Usuario usuario) {
        return MetaAhorro.builder()
                .nombre(request.getNombre())
                .montoObjetivo(request.getMontoObjetivo())
                .montoActual(0.0)
                .fechaLimite(request.getFechaLimite())
                .usuario(usuario)
                .build();
    }

    public MetaAhorroResponse toResponse(MetaAhorro meta, Double montoObjetivoConvertido,
                                         Double montoActualConvertido, String moneda) {
        double porcentaje = (montoObjetivoConvertido > 0)
                ? (montoActualConvertido / montoObjetivoConvertido) * 100
                : 0.0;
        return MetaAhorroResponse.builder()
                .id(meta.getId())
                .nombre(meta.getNombre())
                .montoObjetivo(montoObjetivoConvertido)
                .montoActual(montoActualConvertido)
                .porcentajeCompletado(porcentaje)
                .fechaLimite(meta.getFechaLimite())
                .moneda(moneda)
                .build();
    }
}
