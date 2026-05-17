package com.example.FinalProject.demo.Controller;

import com.example.FinalProject.demo.Dto.Response.DistribucionPorCategoriaResponse;
import com.example.FinalProject.demo.Dto.Response.ResumenMensualResponse;
import com.example.FinalProject.demo.Dto.Response.TendenciaResponse;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Security.SecurityUtils;
import com.example.FinalProject.demo.Service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;
    private final SecurityUtils securityUtils;

    public ReporteController(ReporteService reporteService, SecurityUtils securityUtils) {
        this.reporteService = reporteService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/tendencia")
    public ResponseEntity<TendenciaResponse> tendenciaDiaria(
            @RequestParam int anio,
            @RequestParam int mes,
            @RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(reporteService.obtenerTendenciaDiaria(usuario, anio, mes, moneda));
    }

    @GetMapping("/categorias")
    public ResponseEntity<DistribucionPorCategoriaResponse> distribucionPorCategorias(
            @RequestParam int anio,
            @RequestParam int mes,
            @RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(reporteService.obtenerDistribucionPorCategoria(usuario, anio, mes, moneda));
    }

    @GetMapping("/resumen-mensual")
    public ResponseEntity<ResumenMensualResponse> resumenMensual(
            @RequestParam int anio,
            @RequestParam int mes,
            @RequestParam(defaultValue = "COP") String moneda) {
        Usuario usuario = securityUtils.obtenerUsuarioActual();
        return ResponseEntity.ok(reporteService.obtenerResumenMensual(usuario, anio, mes, moneda));
    }
}
