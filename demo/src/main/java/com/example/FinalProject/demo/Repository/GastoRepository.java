package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.Gasto;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GastoRepository extends JpaRepository<Gasto, UUID> {
    List<Gasto> findByUsuarioOrderByFechaDesc(Usuario usuario);
    List<Gasto> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate inicio, LocalDate fin);
    Optional<Gasto> findByIdAndUsuario(UUID id, Usuario usuario);

    @Query("SELECT SUM(g.monto) FROM Gasto g WHERE g.usuario = :usuario AND g.categoria = :categoria AND YEAR(g.fecha) = :anio AND MONTH(g.fecha) = :mes")
    Double sumarGastosMensualesPorCategoria(@Param("usuario") Usuario usuario,
                                            @Param("categoria") Categoria categoria,
                                            @Param("anio") int anio,
                                            @Param("mes") int mes);

    @Query("SELECT SUM(g.monto) FROM Gasto g WHERE g.usuario = :usuario AND YEAR(g.fecha) = :anio AND MONTH(g.fecha) = :mes")
    Double sumarGastosMensuales(@Param("usuario") Usuario usuario, @Param("anio") int anio, @Param("mes") int mes);

    List<Gasto> findByUsuarioAndFechaActualizacionAfter(Usuario usuario, LocalDateTime fecha);
}
