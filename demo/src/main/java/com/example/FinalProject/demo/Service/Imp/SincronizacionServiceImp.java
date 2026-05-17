package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.SincronizacionRequest;
import com.example.FinalProject.demo.Dto.Response.GastoResponse;
import com.example.FinalProject.demo.Dto.Response.SincronizacionResponse;
import com.example.FinalProject.demo.Model.Gasto;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.GastoRepository;
import com.example.FinalProject.demo.Service.SincronizacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SincronizacionServiceImp implements SincronizacionService {

    private final GastoRepository gastoRepository;

    @Override
    public SincronizacionResponse sincronizar(Usuario usuario, SincronizacionRequest request) {
        LocalDateTime ultima = request.getUltimaSincronizacion() != null
                ? request.getUltimaSincronizacion()
                : LocalDateTime.MIN;

        List<Gasto> nuevosGastos = gastoRepository.findByUsuarioAndFechaActualizacionAfter(usuario, ultima);

        List<GastoResponse> respuestas = nuevosGastos.stream()
                .map(g -> GastoResponse.builder()
                        .id(g.getId())
                        .monto(g.getMonto())
                        .descripcion(g.getDescripcion())
                        .fecha(g.getFecha())
                        .moneda("COP")
                        .build())
                .collect(Collectors.toList());

        return SincronizacionResponse.builder()
                .gastosNuevosOActualizados(respuestas)
                .servidorTimestamp(LocalDateTime.now())
                .build();
    }
}
