package com.example.FinalProject.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "metas_ahorro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private Double montoObjetivo;
    private Double montoActual;
    private LocalDate fechaLimite;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
