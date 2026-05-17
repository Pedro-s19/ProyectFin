package com.example.FinalProject.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "presupuestos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "categoria_id", "anio", "mes"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresupuestoMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private int anio;
    private int mes;
    private Double limiteMonto;


}
