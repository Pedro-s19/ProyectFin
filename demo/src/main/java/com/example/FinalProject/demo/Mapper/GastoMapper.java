package com.example.FinalProject.demo.Mapper;

import com.example.FinalProject.demo.Dto.Request.GastoRequest;
import com.example.FinalProject.demo.Dto.Response.GastoResponse;
import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.Gasto;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class GastoMapper {

    private final CategoriaMapper categoriaMapper;

    public GastoMapper(CategoriaMapper categoriaMapper) {
        this.categoriaMapper = categoriaMapper;
    }
    public Gasto toEntity(GastoRequest request, Categoria categoria, Usuario usuario){
        return Gasto.builder()
                .monto(request.getMonto())
                .descripcion(request.getDescripcion())
                .fecha(request.getFecha())
                .categoria(categoria)
                .usuario(usuario)
                .build();
    }
    public GastoResponse toResponse(Gasto gasto, Double montoConvertido,String moneda){
        return GastoResponse.builder()
                .id(gasto.getId())
                .monto(montoConvertido)
                .descripcion(gasto.getDescripcion())
                .fecha(gasto.getFecha())
                .moneda(moneda)
                .categoria(categoriaMapper.toResponse(gasto.getCategoria()))
                .build();
    }
}
