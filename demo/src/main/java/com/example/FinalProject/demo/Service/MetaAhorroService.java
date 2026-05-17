package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.MetaAhorroRequest;
import com.example.FinalProject.demo.Dto.Response.MetaAhorroResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.util.List;
import java.util.UUID;

public interface MetaAhorroService {

    MetaAhorroResponse crearMeta(Usuario usuario, MetaAhorroRequest request);
    MetaAhorroResponse obtenerMeta(UUID id, Usuario usuario);
    List<MetaAhorroResponse> listarMetas(Usuario usuario, String monedaDestino);
    MetaAhorroResponse agregarAhorro(UUID id, Usuario usuario, Double montoCOP);
    MetaAhorroResponse actualizarMeta(UUID id, Usuario usuario, MetaAhorroRequest request);
    void eliminarMeta(UUID id, Usuario usuario);
}
