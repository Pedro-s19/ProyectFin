package com.example.FinalProject.demo.Dto.Request;

import lombok.Data;

@Data
public class ActualizarUsuarioRequest {

    private String email;
    private String contrasena;
    private String monedaPreferida;
}
