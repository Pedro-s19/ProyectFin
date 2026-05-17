package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.MetaAhorroRequest;
import com.example.FinalProject.demo.Dto.Response.MetaAhorroResponse;
import com.example.FinalProject.demo.Exception.BadRequestException;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Mapper.MetaAhorroMapper;
import com.example.FinalProject.demo.Model.MetaAhorro;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.MetaAhorroRepository;
import com.example.FinalProject.demo.Service.MetaAhorroService;
import com.example.FinalProject.demo.Util.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaAhorroServiceImp implements MetaAhorroService {

    private final MetaAhorroRepository metaAhorroRepository;
    private final MetaAhorroMapper metaAhorroMapper;
    private final CurrencyConverter currencyConverter;

    @Override
    public MetaAhorroResponse crearMeta(Usuario usuario, MetaAhorroRequest request) {
        MetaAhorro meta = metaAhorroMapper.toEntity(request, usuario);
        MetaAhorro guardada = metaAhorroRepository.save(meta);
        return metaAhorroMapper.toResponse(guardada, guardada.getMontoObjetivo(), guardada.getMontoActual(), "COP");
    }

    @Override
    public MetaAhorroResponse obtenerMeta(UUID id, Usuario usuario) {
        MetaAhorro meta = metaAhorroRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Meta no encontrada"));
        return metaAhorroMapper.toResponse(meta, meta.getMontoObjetivo(), meta.getMontoActual(), "COP");
    }

    @Override
    public List<MetaAhorroResponse> listarMetas(Usuario usuario, String monedaDestino) {
        List<MetaAhorro> metas = metaAhorroRepository.findByUsuario(usuario);
        return metas.stream()
                .map(meta -> {
                    double objetivoConv = currencyConverter.convertir(meta.getMontoObjetivo(), monedaDestino);
                    double actualConv = currencyConverter.convertir(meta.getMontoActual(), monedaDestino);
                    return metaAhorroMapper.toResponse(meta, objetivoConv, actualConv, monedaDestino);
                })
                .collect(Collectors.toList());
    }

    @Override
    public MetaAhorroResponse agregarAhorro(UUID id, Usuario usuario, Double montoCOP) {
        MetaAhorro meta = metaAhorroRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Meta no encontrada"));
        if (montoCOP <= 0) throw new BadRequestException("El monto debe ser positivo");
        double nuevoActual = meta.getMontoActual() + montoCOP;
        if (nuevoActual > meta.getMontoObjetivo()) {
            nuevoActual = meta.getMontoObjetivo();
        }
        meta.setMontoActual(nuevoActual);
        MetaAhorro actualizada = metaAhorroRepository.save(meta);
        return metaAhorroMapper.toResponse(actualizada, actualizada.getMontoObjetivo(), actualizada.getMontoActual(), "COP");
    }

    @Override
    public MetaAhorroResponse actualizarMeta(UUID id, Usuario usuario, MetaAhorroRequest request) {
        MetaAhorro meta = metaAhorroRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Meta no encontrada"));
        if (request.getNombre() != null) meta.setNombre(request.getNombre());
        if (request.getMontoObjetivo() != null) meta.setMontoObjetivo(request.getMontoObjetivo());
        if (request.getFechaLimite() != null) meta.setFechaLimite(request.getFechaLimite());
        MetaAhorro actualizada = metaAhorroRepository.save(meta);
        return metaAhorroMapper.toResponse(actualizada, actualizada.getMontoObjetivo(), actualizada.getMontoActual(), "COP");
    }

    @Override
    public void eliminarMeta(UUID id, Usuario usuario) {
        MetaAhorro meta = metaAhorroRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Meta no encontrada"));
        metaAhorroRepository.delete(meta);
    }
}
