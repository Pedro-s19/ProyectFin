package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.LoginRequest;
import com.example.FinalProject.demo.Dto.Request.RegistroRequest;
import com.example.FinalProject.demo.Dto.Response.JwtResponse;
import com.example.FinalProject.demo.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest request) {
        usuarioService.crearUsuario(request);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }
}

