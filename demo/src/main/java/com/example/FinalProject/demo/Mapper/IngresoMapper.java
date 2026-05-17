package com.example.FinalProject.demo.Mapper;

import com.example.FinalProject.demo.Dto.Request.IngresoRequest;
import com.example.FinalProject.demo.Dto.Response.IngresoResponse;
import com.example.FinalProject.demo.Model.Ingreso;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class IngresoMapper {

    public Ingreso toEntity(IngresoRequest request, Usuario usuario) {
        return Ingreso.builder()
                .monto(request.getMonto())
                .descripcion(request.getDescripcion())
                .fecha(request.getFecha())
                .usuario(usuario)
                .build();
    }
    public IngresoResponse toResponse(Ingreso ingreso, Double montoConvertido, String moneda) {
        return IngresoResponse.builder()
                .id(ingreso.getId())
                .monto(montoConvertido)
                .descripcion(ingreso.getDescripcion())
                .fecha(ingreso.getFecha())
                .moneda(moneda)
                .build();
    }
}
