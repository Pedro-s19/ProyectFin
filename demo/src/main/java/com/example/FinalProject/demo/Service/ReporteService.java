package com.example.FinalProject.demo.Service;

import com.example.FinalProject.demo.Dto.Response.DistribucionPorCategoriaResponse;
import com.example.FinalProject.demo.Dto.Response.ResumenMensualResponse;
import com.example.FinalProject.demo.Dto.Response.TendenciaResponse;
import com.example.FinalProject.demo.Model.Usuario;

import java.time.LocalDate;
import java.util.Map;

public interface ReporteService {

    TendenciaResponse obtenerTendenciaDiaria(Usuario usuario, int anio, int mes, String monedaDestino);
    DistribucionPorCategoriaResponse obtenerDistribucionPorCategoria(Usuario usuario, int anio, int mes, String monedaDestino);
    ResumenMensualResponse obtenerResumenMensual(Usuario usuario, int anio, int mes, String monedaDestino);
    Map<LocalDate, Double> obtenerGastosDiarios(Usuario usuario, int anio, int mes, String monedaDestino);
}
