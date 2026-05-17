package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.IngresoRequest;
import com.example.FinalProject.demo.Dto.Response.IngresoResponse;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Mapper.IngresoMapper;
import com.example.FinalProject.demo.Model.Ingreso;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.IngresoRepository;
import com.example.FinalProject.demo.Service.IngresoService;
import com.example.FinalProject.demo.Util.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngresoServiceImp implements IngresoService {

    private final IngresoRepository ingresoRepository;
    private final IngresoMapper ingresoMapper;
    private final CurrencyConverter currencyConverter;

    @Override
    public IngresoResponse crearIngreso(Usuario usuario, IngresoRequest request) {
        Ingreso ingreso = ingresoMapper.toEntity(request, usuario);
        Ingreso guardado = ingresoRepository.save(ingreso);
        return ingresoMapper.toResponse(guardado, guardado.getMonto(), "COP");
    }

    @Override
    public IngresoResponse obtenerIngresoPorId(UUID id, Usuario usuario) {
        Ingreso ingreso = ingresoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado"));
        return ingresoMapper.toResponse(ingreso, ingreso.getMonto(), "COP");
    }

    @Override
    public List<IngresoResponse> listarIngresos(Usuario usuario, String monedaDestino) {
        List<Ingreso> ingresos = ingresoRepository.findByUsuarioOrderByFechaDesc(usuario);
        return ingresos.stream()
                .map(i -> ingresoMapper.toResponse(i,
                        currencyConverter.convertir(i.getMonto(), monedaDestino),
                        monedaDestino))
                .collect(Collectors.toList());
    }

    @Override
    public IngresoResponse actualizarIngreso(UUID id, Usuario usuario, IngresoRequest request) {
        Ingreso ingreso = ingresoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado"));
        ingreso.setMonto(request.getMonto());
        ingreso.setDescripcion(request.getDescripcion());
        ingreso.setFecha(request.getFecha());
        Ingreso actualizado = ingresoRepository.save(ingreso);
        return ingresoMapper.toResponse(actualizado, actualizado.getMonto(), "COP");
    }

    @Override
    public void eliminarIngreso(UUID id, Usuario usuario) {
        Ingreso ingreso = ingresoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado"));
        ingresoRepository.delete(ingreso);
    }
}
