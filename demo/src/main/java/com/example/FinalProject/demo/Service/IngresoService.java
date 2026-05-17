package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.IngresoRequest;
import com.example.FinalProject.demo.Dto.Response.IngresoResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.util.List;
import java.util.UUID;

public interface IngresoService {

    IngresoResponse crearIngreso(Usuario usuario, IngresoRequest request);
    IngresoResponse obtenerIngresoPorId(UUID id, Usuario usuario);
    List<IngresoResponse> listarIngresos(Usuario usuario, String monedaDestino);
    IngresoResponse actualizarIngreso(UUID id, Usuario usuario, IngresoRequest request);
    void eliminarIngreso(UUID id, Usuario usuario);
}
