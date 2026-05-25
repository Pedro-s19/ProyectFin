package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Response.DistribucionPorCategoriaResponse;
import com.example.FinalProject.demo.Dto.Response.ResumenMensualResponse;
import com.example.FinalProject.demo.Dto.Response.TendenciaResponse;
import com.example.FinalProject.demo.Model.Gasto;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.GastoRepository;
import com.example.FinalProject.demo.Repository.IngresoRepository;
import com.example.FinalProject.demo.Service.ReporteService;
import com.example.FinalProject.demo.Util.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteServiceImp implements ReporteService {

    private final GastoRepository gastoRepository;
    private final IngresoRepository ingresoRepository;
    private final CurrencyConverter currencyConverter;

    @Override
    public TendenciaResponse obtenerTendenciaDiaria(Usuario usuario, int anio, int mes, String monedaDestino) {
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        List<Gasto> gastos = gastoRepository.findByUsuarioAndFechaBetween(usuario, inicio, fin);
        Map<LocalDate, Double> mapaDiario = new HashMap<>();
        for (Gasto g : gastos) {
            mapaDiario.merge(g.getFecha(), g.getMonto(), Double::sum);
        }
        List<String> etiquetas = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        for (int dia = 1; dia <= fin.getDayOfMonth(); dia++) {
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            etiquetas.add(String.valueOf(dia));
            double valorCOP = mapaDiario.getOrDefault(fecha, 0.0);
            valores.add(currencyConverter.convertir(valorCOP, monedaDestino));
        }
        return TendenciaResponse.builder()
                .etiquetas(etiquetas)
                .valores(valores)
                .moneda(monedaDestino)
                .build();
    }

    @Override
    public Map<LocalDate, Double> obtenerGastosDiarios(Usuario usuario, int anio, int mes, String monedaDestino) {
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        List<Gasto> gastos = gastoRepository.findByUsuarioAndFechaBetween(usuario, inicio, fin);
        Map<LocalDate, Double> mapa = new HashMap<>();
        for (Gasto g : gastos) {
            mapa.merge(g.getFecha(), g.getMonto(), Double::sum);
        }
        Map<LocalDate, Double> resultado = new HashMap<>();
        for (Map.Entry<LocalDate, Double> entry : mapa.entrySet()) {
            resultado.put(entry.getKey(), currencyConverter.convertir(entry.getValue(), monedaDestino));
        }
        return resultado;
    }

    @Override
    public DistribucionPorCategoriaResponse obtenerDistribucionPorCategoria(Usuario usuario, int anio, int mes, String monedaDestino) {
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        List<Gasto> gastos = gastoRepository.findByUsuarioAndFechaBetween(usuario, inicio, fin);
        Map<String, Double> totalPorCategoria = new HashMap<>();
        for (Gasto g : gastos) {
            String nombreCat = g.getCategoria().getNombre();
            totalPorCategoria.merge(nombreCat, g.getMonto(), Double::sum);
        }
        double totalGeneral = totalPorCategoria.values().stream().mapToDouble(Double::doubleValue).sum();
        List<DistribucionPorCategoriaResponse.ItemCategoriaGasto> items = new ArrayList<>();
        for (Map.Entry<String, Double> entry : totalPorCategoria.entrySet()) {
            double valorCOP = entry.getValue();
            double valorConvertido = currencyConverter.convertir(valorCOP, monedaDestino);
            double porcentaje = totalGeneral > 0 ? (valorCOP / totalGeneral) * 100 : 0;
            items.add(DistribucionPorCategoriaResponse.ItemCategoriaGasto.builder()
                    .categoriaNombre(entry.getKey())
                    .total(valorConvertido)
                    .porcentaje(porcentaje)
                    .build());
        }
        return DistribucionPorCategoriaResponse.builder()
                .items(items)
                .moneda(monedaDestino)
                .build();
    }

    @Override
    public ResumenMensualResponse obtenerResumenMensual(Usuario usuario, int anio, int mes, String monedaDestino) {
        Double totalGastosCOP = gastoRepository.sumarGastosMensuales(usuario, anio, mes);
        Double totalIngresosCOP = ingresoRepository.sumarIngresosMensuales(usuario, anio, mes);
        double gastos = totalGastosCOP != null ? totalGastosCOP : 0.0;
        double ingresos = totalIngresosCOP != null ? totalIngresosCOP : 0.0;
        double balance = ingresos - gastos;
        double presupuestoTotal = ingresos * 0.8;
        double presupuestoRestante = presupuestoTotal - gastos;
        double porcentajeUsado = presupuestoTotal > 0 ? (gastos / presupuestoTotal) * 100 : 0;
        return ResumenMensualResponse.builder()
                .totalGastos(currencyConverter.convertir(gastos, monedaDestino))
                .totalIngresos(currencyConverter.convertir(ingresos, monedaDestino))
                .balance(currencyConverter.convertir(balance, monedaDestino))
                .presupuestoRestante(currencyConverter.convertir(presupuestoRestante, monedaDestino))
                .porcentajePresupuestoUsado(porcentajeUsado)
                .moneda(monedaDestino)
                .build();
    }
}
