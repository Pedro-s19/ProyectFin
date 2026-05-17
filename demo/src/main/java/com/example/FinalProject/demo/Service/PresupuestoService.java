package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.PresupuestoRequest;
import com.example.FinalProject.demo.Dto.Response.PresupuestoResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.util.List;
import java.util.UUID;

public interface PresupuestoService {

    PresupuestoResponse crearPresupuesto(Usuario usuario, PresupuestoRequest request);
    PresupuestoResponse obtenerPresupuesto(UUID id, Usuario usuario);
    List<PresupuestoResponse> listarPresupuestos(Usuario usuario, int anio, int mes, String monedaDestino);
    PresupuestoResponse actualizarPresupuesto(UUID id, Usuario usuario, PresupuestoRequest request);
    void eliminarPresupuesto(UUID id, Usuario usuario);
}
