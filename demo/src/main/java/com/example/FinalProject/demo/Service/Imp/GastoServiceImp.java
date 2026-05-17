package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.GastoRequest;
import com.example.FinalProject.demo.Dto.Response.GastoResponse;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Mapper.GastoMapper;
import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.Gasto;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.CategoriaRepository;
import com.example.FinalProject.demo.Repository.GastoRepository;
import com.example.FinalProject.demo.Service.GastoService;
import com.example.FinalProject.demo.Util.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoServiceImp implements GastoService {

    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;
    private final GastoMapper gastoMapper;
    private final CurrencyConverter currencyConverter;

    @Override
    public GastoResponse crearGasto(Usuario usuario, GastoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        Gasto gasto = gastoMapper.toEntity(request, categoria, usuario);
        Gasto guardado = gastoRepository.save(gasto);
        return gastoMapper.toResponse(guardado, guardado.getMonto(), "COP");
    }

    @Override
    public GastoResponse obtenerGastoPorId(UUID id, Usuario usuario) {
        Gasto gasto = gastoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        return gastoMapper.toResponse(gasto, gasto.getMonto(), "COP");
    }

    @Override
    public List<GastoResponse> listarGastos(Usuario usuario, String monedaDestino) {
        List<Gasto> gastos = gastoRepository.findByUsuarioOrderByFechaDesc(usuario);
        return gastos.stream()
                .map(g -> gastoMapper.toResponse(g,
                        currencyConverter.convertir(g.getMonto(), monedaDestino),
                        monedaDestino))
                .collect(Collectors.toList());
    }

    @Override
    public GastoResponse actualizarGasto(UUID id, Usuario usuario, GastoRequest request) {
        Gasto gasto = gastoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        gasto.setMonto(request.getMonto());
        gasto.setDescripcion(request.getDescripcion());
        gasto.setFecha(request.getFecha());
        gasto.setCategoria(categoria);
        Gasto actualizado = gastoRepository.save(gasto);
        return gastoMapper.toResponse(actualizado, actualizado.getMonto(), "COP");
    }

    @Override
    public void eliminarGasto(UUID id, Usuario usuario) {
        Gasto gasto = gastoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        gastoRepository.delete(gasto);
    }
}
