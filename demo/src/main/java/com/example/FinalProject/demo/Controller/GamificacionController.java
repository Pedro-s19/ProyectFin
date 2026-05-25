package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Response.LogroResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.GamificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gamificacion")
public class GamificacionController {
    private final GamificacionService gamificacionService;
    private final SecurityUtils securityUtils;

    public GamificacionController(GamificacionService gamificacionService, SecurityUtils securityUtils) {
        this.gamificacionService = gamificacionService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/logros")
    public ResponseEntity<List<LogroResponse>> listarLogros() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(gamificacionService.listarLogros(usuario));
    }

    @GetMapping("/puntos")
    public ResponseEntity<Integer> obtenerPuntos() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(gamificacionService.obtenerPuntos(usuario));
    }
}
