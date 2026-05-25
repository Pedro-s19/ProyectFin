package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.GastoRecurrenteRequest;
import com.example.FinalProject.demo.Dto.Response.GastoRecurrenteResponse;
import com.example.FinalProject.demo.Model.Usuario;
import java.util.List;
import java.util.UUID;

public interface GastoRecurrenteService {
    List<GastoRecurrenteResponse> listar(Usuario usuario);
    GastoRecurrenteResponse crear(Usuario usuario, GastoRecurrenteRequest request);
    GastoRecurrenteResponse actualizar(UUID id, Usuario usuario, GastoRecurrenteRequest request);
    void eliminar(UUID id, Usuario usuario);
    void generarGastosDelDia();
}
