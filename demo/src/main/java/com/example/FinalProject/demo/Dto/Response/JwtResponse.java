package com.example.FinalProject.demo.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String tipo = "Bearer";
    private String email;
    private String rol;
}
