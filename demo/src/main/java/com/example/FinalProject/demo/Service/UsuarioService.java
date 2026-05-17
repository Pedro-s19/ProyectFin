package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Request.ActualizarUsuarioRequest;
import com.example.FinalProject.demo.Dto.Request.LoginRequest;
import com.example.FinalProject.demo.Dto.Request.RegistroRequest;
import com.example.FinalProject.demo.Dto.Response.JwtResponse;
import com.example.FinalProject.demo.Dto.Response.UsuarioResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    UsuarioResponse crearUsuario(RegistroRequest request);
    JwtResponse login(LoginRequest request);
    UsuarioResponse obtenerUsuarioPorId(UUID id);
    List<UsuarioResponse> listarUsuarios();
    UsuarioResponse actualizarUsuario(UUID id, ActualizarUsuarioRequest request);
    void eliminarUsuario(UUID id);
}
