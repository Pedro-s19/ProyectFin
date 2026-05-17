package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UsuarioResponse {

    private UUID id;
    private String email;
    private String monedaPreferida;
    private boolean activo;

}
