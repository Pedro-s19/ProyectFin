package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.IngresoRequest;
import com.example.FinalProject.demo.Dto.Response.IngresoResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.IngresoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    private final IngresoService ingresoService;
    private final SecurityUtils securityUtils;

    public IngresoController(IngresoService ingresoService, SecurityUtils securityUtils) {
        this.ingresoService = ingresoService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<IngresoResponse>> listar(@RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(ingresoService.listarIngresos(usuario, moneda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponse> obtener(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(ingresoService.obtenerIngresoPorId(id, usuario));
    }

    @PostMapping
    public ResponseEntity<IngresoResponse> crear(@RequestBody IngresoRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        IngresoResponse response = ingresoService.crearIngreso(usuario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngresoResponse> actualizar(@PathVariable UUID id, @RequestBody IngresoRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(ingresoService.actualizarIngreso(id, usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        ingresoService.eliminarIngreso(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
