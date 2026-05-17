package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.GastoRequest;
import com.example.FinalProject.demo.Dto.Response.GastoResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.GastoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private final GastoService gastoService;
    private final SecurityUtils securityUtils;

    public GastoController(GastoService gastoService, SecurityUtils securityUtils) {
        this.gastoService = gastoService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<GastoResponse>> listar(@RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(gastoService.listarGastos(usuario, moneda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoResponse> obtener(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(gastoService.obtenerGastoPorId(id, usuario));
    }

    @PostMapping
    public ResponseEntity<GastoResponse> crear(@RequestBody GastoRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        GastoResponse response = gastoService.crearGasto(usuario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoResponse> actualizar(@PathVariable UUID id, @RequestBody GastoRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(gastoService.actualizarGasto(id, usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        gastoService.eliminarGasto(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
