package com.example.FinalProject.demo.Mapper;

import com.example.FinalProject.demo.Dto.Request.PresupuestoRequest;
import com.example.FinalProject.demo.Dto.Response.PresupuestoResponse;
import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.PresupuestoMensual;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PresupuestoMapper {
    public PresupuestoMensual toEntity(PresupuestoRequest request, Usuario usuario, Categoria categoria){

        return PresupuestoMensual.builder()
                .usuario(usuario)
                .categoria(categoria)
                .anio(request.getAnio())
                .mes(request.getMes())
                .limiteMonto(request.getLimiteMonto())
                .build();
    }
    public PresupuestoResponse toResponse(PresupuestoMensual presupuesto, Double gastadoMonto, String moneda) {
        return PresupuestoResponse.builder()
                .id(presupuesto.getId())
                .categoriaId(presupuesto.getCategoria().getId())
                .categoriaNombre(presupuesto.getCategoria().getNombre())
                .anio(presupuesto.getAnio())
                .mes(presupuesto.getMes())
                .limiteMonto(presupuesto.getLimiteMonto())
                .gastadoMonto(gastadoMonto)
                .moneda(moneda)
                .build();
    }
}
