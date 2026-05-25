package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Response.LogroResponse;
import com.example.FinalProject.demo.Model.Usuario;
import java.util.List;

public interface GamificacionService {
    List<LogroResponse> listarLogros(Usuario usuario);
    int obtenerPuntos(Usuario usuario);
    void verificarYOtorgar(Usuario usuario, String evento);   // evento = "gasto_creado", "meta_cumplida", etc.
}
