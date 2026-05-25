package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Response.AlertaResponse;
import com.example.FinalProject.demo.Model.Usuario;

public interface AlertaService {
    AlertaResponse obtenerAlertas(Usuario usuario);
}
