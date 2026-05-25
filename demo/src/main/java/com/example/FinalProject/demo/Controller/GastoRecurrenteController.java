package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.GastoRecurrenteRequest;
import com.example.FinalProject.demo.Dto.Response.GastoRecurrenteResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.GastoRecurrenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recurrentes")
public class GastoRecurrenteController {
    private final GastoRecurrenteService service;
    private final SecurityUtils securityUtils;

    public GastoRecurrenteController(GastoRecurrenteService service, SecurityUtils securityUtils) {
        this.service = service;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<GastoRecurrenteResponse>> listar() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(service.listar(usuario));
    }

    @PostMapping
    public ResponseEntity<GastoRecurrenteResponse> crear(@RequestBody GastoRecurrenteRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(usuario, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoRecurrenteResponse> actualizar(@PathVariable UUID id, @RequestBody GastoRecurrenteRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(service.actualizar(id, usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        service.eliminar(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
