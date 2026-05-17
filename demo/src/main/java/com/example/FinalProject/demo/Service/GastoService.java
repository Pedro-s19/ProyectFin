package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.GastoRequest;
import com.example.FinalProject.demo.Dto.Response.GastoResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.util.List;
import java.util.UUID;

public interface GastoService {

    GastoResponse crearGasto(Usuario usuario, GastoRequest request);
    GastoResponse obtenerGastoPorId(UUID id, Usuario usuario);
    List<GastoResponse> listarGastos(Usuario usuario, String monedaDestino);
    GastoResponse actualizarGasto(UUID id, Usuario usuario, GastoRequest request);
    void eliminarGasto(UUID id, Usuario usuario);

}
