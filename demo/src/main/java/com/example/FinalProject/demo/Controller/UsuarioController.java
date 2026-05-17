package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.ActualizarUsuarioRequest;
import com.example.FinalProject.demo.Dto.Response.UsuarioResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SecurityUtils securityUtils;

    public UsuarioController(UsuarioService usuarioService, SecurityUtils securityUtils) {
        this.usuarioService = usuarioService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> obtenerMiPerfil() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(usuario.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> actualizarMiPerfil(@RequestBody ActualizarUsuarioRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuario.getId(), request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> eliminarMiCuenta() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        usuarioService.eliminarUsuario(usuario.getId());
        return ResponseEntity.noContent().build();
    }
}
