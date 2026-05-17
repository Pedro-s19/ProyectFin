package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.PresupuestoRequest;
import com.example.FinalProject.demo.Dto.Response.PresupuestoResponse;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Mapper.PresupuestoMapper;
import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.PresupuestoMensual;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.CategoriaRepository;
import com.example.FinalProject.demo.Repository.GastoRepository;
import com.example.FinalProject.demo.Repository.PresupuestoRepository;
import com.example.FinalProject.demo.Service.PresupuestoService;
import com.example.FinalProject.demo.Util.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresupuestoServiceImp implements PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final CategoriaRepository categoriaRepository;
    private final GastoRepository gastoRepository;
    private final PresupuestoMapper presupuestoMapper;
    private final CurrencyConverter currencyConverter;

    @Override
    public PresupuestoResponse crearPresupuesto(Usuario usuario, PresupuestoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        PresupuestoMensual presupuesto = presupuestoMapper.toEntity(request, usuario, categoria);
        PresupuestoMensual guardado = presupuestoRepository.save(presupuesto);
        double gastado = obtenerGastadoEnCategoria(usuario, categoria, request.getAnio(), request.getMes());
        return presupuestoMapper.toResponse(guardado, gastado, "COP");
    }

    @Override
    public PresupuestoResponse obtenerPresupuesto(UUID id, Usuario usuario) {
        PresupuestoMensual presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));
        if (!presupuesto.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No autorizado");
        }
        double gastado = obtenerGastadoEnCategoria(usuario, presupuesto.getCategoria(),
                presupuesto.getAnio(), presupuesto.getMes());
        return presupuestoMapper.toResponse(presupuesto, gastado, "COP");
    }

    @Override
    public List<PresupuestoResponse> listarPresupuestos(Usuario usuario, int anio, int mes, String monedaDestino) {
        List<PresupuestoMensual> presupuestos = presupuestoRepository.findByUsuarioAndAnioAndMes(usuario, anio, mes);
        return presupuestos.stream()
                .map(p -> {
                    double gastado = obtenerGastadoEnCategoria(usuario, p.getCategoria(), anio, mes);
                    double gastadoConvertido = currencyConverter.convertir(gastado, monedaDestino);
                    return presupuestoMapper.toResponse(p, gastadoConvertido, monedaDestino);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PresupuestoResponse actualizarPresupuesto(UUID id, Usuario usuario, PresupuestoRequest request) {
        PresupuestoMensual presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));
        if (!presupuesto.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No autorizado");
        }
        presupuesto.setLimiteMonto(request.getLimiteMonto());
        PresupuestoMensual actualizado = presupuestoRepository.save(presupuesto);
        double gastado = obtenerGastadoEnCategoria(usuario, presupuesto.getCategoria(),
                presupuesto.getAnio(), presupuesto.getMes());
        return presupuestoMapper.toResponse(actualizado, gastado, "COP");
    }

    @Override
    public void eliminarPresupuesto(UUID id, Usuario usuario) {
        PresupuestoMensual presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));
        if (!presupuesto.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No autorizado");
        }
        presupuestoRepository.delete(presupuesto);
    }

    private double obtenerGastadoEnCategoria(Usuario usuario, Categoria categoria, int anio, int mes) {
        Double gastado = gastoRepository.sumarGastosMensualesPorCategoria(usuario, categoria, anio, mes);
        return gastado != null ? gastado : 0.0;
    }
}
