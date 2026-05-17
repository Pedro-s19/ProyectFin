package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.MetaAhorroRequest;
import com.example.FinalProject.demo.Dto.Response.MetaAhorroResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.MetaAhorroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metas-ahorro")
public class MetaAhorroController {

    private final MetaAhorroService metaAhorroService;
    private final SecurityUtils securityUtils;

    public MetaAhorroController(MetaAhorroService metaAhorroService, SecurityUtils securityUtils) {
        this.metaAhorroService = metaAhorroService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<MetaAhorroResponse>> listar(@RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(metaAhorroService.listarMetas(usuario, moneda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaAhorroResponse> obtener(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(metaAhorroService.obtenerMeta(id, usuario));
    }

    @PostMapping
    public ResponseEntity<MetaAhorroResponse> crear(@RequestBody MetaAhorroRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        MetaAhorroResponse response = metaAhorroService.crearMeta(usuario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/agregar-ahorro")
    public ResponseEntity<MetaAhorroResponse> agregarAhorro(@PathVariable UUID id, @RequestParam Double montoCOP) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(metaAhorroService.agregarAhorro(id, usuario, montoCOP));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaAhorroResponse> actualizar(@PathVariable UUID id, @RequestBody MetaAhorroRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(metaAhorroService.actualizarMeta(id, usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        metaAhorroService.eliminarMeta(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
