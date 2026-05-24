package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Request.ActualizarUsuarioRequest;
import com.example.FinalProject.demo.Dto.Request.LoginRequest;
import com.example.FinalProject.demo.Dto.Request.RegistroRequest;
import com.example.FinalProject.demo.Dto.Response.JwtResponse;
import com.example.FinalProject.demo.Dto.Response.UsuarioResponse;
import com.example.FinalProject.demo.Exception.BadRequestException;
import com.example.FinalProject.demo.Exception.ResourceNotFoundException;
import com.example.FinalProject.demo.Mapper.UsuarioMapper;
import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.UsuarioRepository;
import com.example.FinalProject.demo.Security.JwtTokenProvider;
import com.example.FinalProject.demo.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse crearUsuario(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        Usuario usuario = usuarioMapper.toEntity(request, passwordEncoder);
        Usuario guardado = usuarioRepository.save(usuario);
        return toResponse(guardado);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena())
        );
        String token = tokenProvider.generarToken(authentication);
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return new JwtResponse(token, "Bearer", request.getEmail(), usuario.getRol());
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return toResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse actualizarUsuario(UUID id, ActualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (request.getEmail() != null && !request.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("El email ya existe");
            }
            usuario.setEmail(request.getEmail());
        }
        if (request.getMonedaPreferida() != null) {
            usuario.setMonedaPreferida(request.getMonedaPreferida());
        }
        if (request.getContrasena() != null && !request.getContrasena().isEmpty()) {
            usuario.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        }
        Usuario actualizado = usuarioRepository.save(usuario);
        return toResponse(actualizado);
    }

    @Override
    public void eliminarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .monedaPreferida(usuario.getMonedaPreferida())
                .activo(usuario.isActivo())
                .build();
    }
}

