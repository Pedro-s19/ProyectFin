package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Response.AlertaResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final SecurityUtils securityUtils;

    public AlertaController(AlertaService alertaService, SecurityUtils securityUtils) {
        this.alertaService = alertaService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<AlertaResponse> obtenerAlertas() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(alertaService.obtenerAlertas(usuario));
    }
}
