package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.CategoriaRequest;
import com.example.FinalProject.demo.Dto.Response.CategoriaResponse;
import com.example.FinalProject.demo.Exception.BadRequestException;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Mapper.CategoriaMapper;
import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.CategoriaRepository;
import com.example.FinalProject.demo.Service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public CategoriaResponse crearCategoria(Usuario usuario, CategoriaRequest request) {
        if (categoriaRepository.existsByNombreAndUsuario(request.getNombre(), usuario)) {
            throw new BadRequestException("Ya tienes una categoría con ese nombre");
        }
        Categoria categoria = categoriaMapper.toEntity(request, usuario);
        Categoria guardada = categoriaRepository.save(categoria);
        return categoriaMapper.toResponse(guardada);
    }

    @Override
    public CategoriaResponse obtenerCategoriaPorId(UUID id, Usuario usuario) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        if (categoria.getUsuario() != null && !categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tienes permiso para ver esta categoría");
        }
        return categoriaMapper.toResponse(categoria);
    }

    @Override
    public List<CategoriaResponse> listarCategorias(Usuario usuario) {
        List<Categoria> categorias = categoriaRepository.findAllParaUsuario(usuario);
        return categorias.stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponse actualizarCategoria(UUID id, Usuario usuario, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No puedes modificar esta categoría");
        }
        categoria.setNombre(request.getNombre());
        categoria.setIcono(request.getIcono());
        Categoria actualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toResponse(actualizada);
    }

    @Override
    public void eliminarCategoria(UUID id, Usuario usuario) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No puedes eliminar esta categoría");
        }
        categoriaRepository.delete(categoria);
    }
}
