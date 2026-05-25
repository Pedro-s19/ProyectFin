package com.example.FinalProject.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "logros")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Logro {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private String descripcion;
    private String icono;
    private String condicion;
    private int puntos;
}
