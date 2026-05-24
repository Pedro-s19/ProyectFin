package com.example.FinalProject.demo.Mapper;

import com.example.FinalProject.demo.Dto.Request.RegistroRequest;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toEntity(RegistroRequest request, PasswordEncoder encoder) {
        return Usuario.builder()
                .email(request.getEmail())
                .contrasenaHash(encoder.encode(request.getContrasena()))
                .monedaPreferida("COP")
                .monedaBase("COP")
                .rol("ROLE_USER")
                .activo(true)
                .build();
    }
}
