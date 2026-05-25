package com.example.FinalProject.demo.Service.Imp;

import com.example.FinalProject.demo.Dto.Response.LogroResponse;
import com.example.FinalProject.demo.Model.*;
import com.example.FinalProject.demo.Repository.*;
import com.example.FinalProject.demo.Service.GamificacionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class GamificacionServiceImp implements GamificacionService {

    private final LogroRepository logroRepository;
    private final UsuarioLogroRepository usuarioLogroRepository;

    @PostConstruct
    public void initLogros() {
        if (logroRepository.count() == 0) {
            logroRepository.save(Logro.builder().nombre("Primer gasto").descripcion("Registra tu primer gasto").icono("🔥").puntos(10).build());
            logroRepository.save(Logro.builder().nombre("50 gastos").descripcion("Llevas 50 gastos registrados").icono("💪").puntos(50).build());
            logroRepository.save(Logro.builder().nombre("Meta cumplida").descripcion("Completaste una meta de ahorro").icono("🎯").puntos(100).build());
            logroRepository.save(Logro.builder().nombre("Presupuesto maestro").descripcion("No superaste ningún presupuesto este mes").icono("🏆").puntos(200).build());
        }
    }


    @Override
    public List<LogroResponse> listarLogros(Usuario usuario) {
        List<Logro> todos = logroRepository.findAll();
        List<UUID> desbloqueados = usuarioLogroRepository.findByUsuario(usuario)
                .stream().map(ul -> ul.getLogro().getId()).collect(Collectors.toList());
        return todos.stream().map(logro -> LogroResponse.builder()
                .id(logro.getId())
                .nombre(logro.getNombre())
                .descripcion(logro.getDescripcion())
                .icono(logro.getIcono())
                .puntos(logro.getPuntos())
                .desbloqueado(desbloqueados.contains(logro.getId()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public int obtenerPuntos(Usuario usuario) {
        return usuarioLogroRepository.findByUsuario(usuario).stream()
                .mapToInt(ul -> ul.getLogro().getPuntos()).sum();
    }

    @Override
    public void verificarYOtorgar(Usuario usuario, String evento) {

        String nombreLogro = switch (evento) {
            case "primer_gasto" -> "Primer gasto";
            case "gasto_50" -> "50 gastos registrados";
            case "meta_cumplida" -> "Meta cumplida";
            default -> null;
        };
        if (nombreLogro == null) return;
        Logro logro = logroRepository.findAll().stream()
                .filter(l -> l.getNombre().equals(nombreLogro)).findFirst().orElse(null);
        if (logro != null && !usuarioLogroRepository.existsByUsuarioAndLogroId(usuario, logro.getId())) {
            usuarioLogroRepository.save(UsuarioLogro.builder()
                    .usuario(usuario).logro(logro).fechaDesbloqueo(LocalDateTime.now()).build());
        }

    }
}
