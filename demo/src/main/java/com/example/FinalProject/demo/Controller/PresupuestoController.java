package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.PresupuestoRequest;
import com.example.FinalProject.demo.Dto.Response.PresupuestoResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.PresupuestoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    private final PresupuestoService presupuestoService;
    private final SecurityUtils securityUtils;

    public PresupuestoController(PresupuestoService presupuestoService, SecurityUtils securityUtils) {
        this.presupuestoService = presupuestoService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<PresupuestoResponse>> listar(
            @RequestParam int anio,
            @RequestParam int mes,
            @RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(presupuestoService.listarPresupuestos(usuario, anio, mes, moneda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresupuestoResponse> obtener(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(presupuestoService.obtenerPresupuesto(id, usuario));
    }

    @PostMapping
    public ResponseEntity<PresupuestoResponse> crear(@RequestBody PresupuestoRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        PresupuestoResponse response = presupuestoService.crearPresupuesto(usuario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresupuestoResponse> actualizar(@PathVariable UUID id, @RequestBody PresupuestoRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(presupuestoService.actualizarPresupuesto(id, usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        presupuestoService.eliminarPresupuesto(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
