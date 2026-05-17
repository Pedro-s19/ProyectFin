package com.example.FinalProject.demo.Security;

import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UsuarioRepository usuarioRepository;

    public Usuario obtenerUsuarioActual(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Usuario no autenticado"));

    }
}
