package com.example.FinalProject.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ingresos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double monto;
    private String descripcion;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaCreacion;

}
