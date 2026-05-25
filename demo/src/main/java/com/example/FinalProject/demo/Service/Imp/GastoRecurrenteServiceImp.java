package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.GastoRecurrenteRequest;
import com.example.FinalProject.demo.Dto.Response.GastoRecurrenteResponse;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Model.*;
import com.example.FinalProject.demo.Repository.*;
import com.example.FinalProject.demo.Service.GastoRecurrenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class GastoRecurrenteServiceImp implements GastoRecurrenteService {
    private final GastoRecurrenteRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final GastoRepository gastoRepository;

    @Override
    public List<GastoRecurrenteResponse> listar(Usuario usuario) {
        return repository.findByUsuarioAndActivoTrue(usuario).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GastoRecurrenteResponse crear(Usuario usuario, GastoRecurrenteRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        GastoRecurrente gr = GastoRecurrente.builder()
                .nombre(request.getNombre())
                .monto(request.getMonto())
                .diaMes(request.getDiaMes())
                .categoria(categoria)
                .usuario(usuario)
                .activo(true)
                .build();
        return toResponse(repository.save(gr));
    }

    @Override
    public GastoRecurrenteResponse actualizar(UUID id, Usuario usuario, GastoRecurrenteRequest request) {
        GastoRecurrente gr = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        // verificar pertenencia...
        if (request.getNombre() != null) gr.setNombre(request.getNombre());
        if (request.getMonto() != null) gr.setMonto(request.getMonto());
        gr.setDiaMes(request.getDiaMes());
        return toResponse(repository.save(gr));
    }

    @Override
    public void eliminar(UUID id, Usuario usuario) {
        GastoRecurrente gr = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        gr.setActivo(false);
        repository.save(gr);
    }

    @Override
    public void generarGastosDelDia() {
        int diaHoy = LocalDate.now().getDayOfMonth();
        List<GastoRecurrente> recurrentes = repository.findByDiaMesAndActivoTrue(diaHoy);
        for (GastoRecurrente gr : recurrentes) {
            Gasto gasto = Gasto.builder()
                    .monto(gr.getMonto())
                    .descripcion(gr.getNombre() + " (recurrente)")
                    .fecha(LocalDate.now())
                    .categoria(gr.getCategoria())
                    .usuario(gr.getUsuario())
                    .build();
            gastoRepository.save(gasto);
        }
    }

    private GastoRecurrenteResponse toResponse(GastoRecurrente gr) {
        return GastoRecurrenteResponse.builder()
                .id(gr.getId())
                .nombre(gr.getNombre())
                .monto(gr.getMonto())
                .diaMes(gr.getDiaMes())
                .activo(gr.isActivo())
                .categoriaNombre(gr.getCategoria().getNombre())
                .categoriaId(gr.getCategoria().getId())
                .build();
    }
}