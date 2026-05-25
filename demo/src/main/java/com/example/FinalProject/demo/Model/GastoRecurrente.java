package com.example.FinalProject.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "gastos_recurrentes")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GastoRecurrente {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private Double monto;
    private int diaMes;          // día del mes en que se cobra (1-28)
    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
