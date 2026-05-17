package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.SincronizacionRequest;
import com.example.FinalProject.demo.Dto.Response.SincronizacionResponse;
import com.example.FinalProject.demo.Model.Usuario;

public interface SincronizacionService {

    SincronizacionResponse sincronizar(Usuario usuario, SincronizacionRequest request);
}
