package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Request.CategoriaRequest;
import com.example.FinalProject.demo.Dto.Response.CategoriaResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final SecurityUtils securityUtils;

    public CategoriaController(CategoriaService categoriaService, SecurityUtils securityUtils) {
        this.categoriaService = categoriaService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(categoriaService.listarCategorias(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtener(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id, usuario));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@RequestBody CategoriaRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        CategoriaResponse response = categoriaService.crearCategoria(usuario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> actualizar(@PathVariable UUID id, @RequestBody CategoriaRequest request) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        categoriaService.eliminarCategoria(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
