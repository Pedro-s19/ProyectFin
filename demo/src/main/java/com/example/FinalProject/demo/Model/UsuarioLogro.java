package com.example.FinalProject.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "usuarios_logros")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UsuarioLogro {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne @JoinColumn(name = "logro_id")
    private Logro logro;

    private LocalDateTime fechaDesbloqueo;
}
