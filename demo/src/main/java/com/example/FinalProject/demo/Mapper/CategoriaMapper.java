package com.example.FinalProject.demo.Mapper;

import com.example.FinalProject.demo.Dto.Request.CategoriaRequest;
import com.example.FinalProject.demo.Dto.Response.CategoriaResponse;
import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public Categoria toEntity(CategoriaRequest request, Usuario usuario){
        return Categoria.builder()
                .nombre(request.getNombre())
                .icono(request.getIcono())
                .usuario(usuario)
                .esPorDefecto(false)
                .build();
    }
    public CategoriaResponse toResponse(Categoria categoria){
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .icono(categoria.getIcono())
                .esPorDefecto(categoria.isEsPorDefecto())
                .build();
    }
}
