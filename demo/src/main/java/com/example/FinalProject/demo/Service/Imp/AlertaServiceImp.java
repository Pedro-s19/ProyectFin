package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Response.AlertaResponse;
import com.example.FinalProject.demo.Model.*;
import com.example.FinalProject.demo.Repository.*;
import com.example.FinalProject.demo.Service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class AlertaServiceImp implements AlertaService {

    private final GastoRepository gastoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final MetaAhorroRepository metaAhorroRepository;
    private final GastoRecurrenteRepository gastoRecurrenteRepository;   // ✅ si añadiste el opcional

    @Override
    public AlertaResponse obtenerAlertas(Usuario usuario) {
        List<String> alertas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        int mes = hoy.getMonthValue();
        int anio = hoy.getYear();


        List<PresupuestoMensual> presupuestos = presupuestoRepository.findByUsuarioAndAnioAndMes(usuario, anio, mes);
        for (PresupuestoMensual p : presupuestos) {
            Double gastado = gastoRepository.sumarGastosMensualesPorCategoria(usuario, p.getCategoria(), anio, mes);
            if (gastado != null && p.getLimiteMonto() > 0 && gastado > p.getLimiteMonto() * 0.8) {
                int porcentaje = (int) (gastado / p.getLimiteMonto() * 100);
                alertas.add("Presupuesto " + p.getCategoria().getNombre() + " al " + porcentaje + "%");
            }
        }


        LocalDate hace3 = hoy.minusDays(3);
        List<Gasto> recientes = gastoRepository.findByUsuarioAndFechaBetween(usuario, hace3, hoy);
        if (recientes.isEmpty()) {
            alertas.add("¡No has registrado gastos en los últimos 3 días!");
        }


        List<MetaAhorro> metas = metaAhorroRepository.findByUsuario(usuario);
        for (MetaAhorro m : metas) {
            if (m.getMontoActual() >= m.getMontoObjetivo()) {
                alertas.add("¡Meta cumplida: " + m.getNombre() + "!");
            }
        }


        LocalDate fechaInicio = hoy.plusDays(1);
        LocalDate fechaFin = hoy.plusDays(3);
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            if (fecha.getMonthValue() != mes) continue;
            int dia = fecha.getDayOfMonth();
            List<GastoRecurrente> recurrentes = gastoRecurrenteRepository.findByDiaMesAndActivoTrue(dia);
            for (GastoRecurrente gr : recurrentes) {
                if (gr.getUsuario().getId().equals(usuario.getId())) {
                    alertas.add("Próximo pago: " + gr.getNombre() + " el día " + dia + " (" + gr.getMonto() + " COP)");
                }
            }
        }

        return AlertaResponse.builder().alertas(alertas).build();
    }
}
