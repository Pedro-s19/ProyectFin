package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.SincronizacionRequest;
import com.example.FinalProject.demo.Dto.Response.SincronizacionResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.SincronizacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sincronizacion")
public class SincronizacionController {

    private final SincronizacionService sincronizacionService;
    private final SecurityUtils securityUtils;

    public SincronizacionController(SincronizacionService sincronizacionService, SecurityUtils securityUtils) {
        this.sincronizacionService = sincronizacionService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    public ResponseEntity<SincronizacionResponse> sincronizar(@RequestBody SincronizacionRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(sincronizacionService.sincronizar(usuario, request));
    }
}
