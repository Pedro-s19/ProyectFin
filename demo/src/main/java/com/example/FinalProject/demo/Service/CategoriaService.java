package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.CategoriaRequest;
import com.example.FinalProject.demo.Dto.Response.CategoriaResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.util.List;
import java.util.UUID;

public interface CategoriaService {

    CategoriaResponse crearCategoria(Usuario usuario, CategoriaRequest request);
    CategoriaResponse obtenerCategoriaPorId(UUID id, Usuario usuario);
    List<CategoriaResponse> listarCategorias(Usuario usuario);
    CategoriaResponse actualizarCategoria(UUID id, Usuario usuario, CategoriaRequest request);
    void eliminarCategoria(UUID id, Usuario usuario);
}
