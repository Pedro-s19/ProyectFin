package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.Ingreso;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngresoRepository extends JpaRepository<Ingreso, UUID> {
    List<Ingreso> findByUsuarioOrderByFechaDesc(Usuario usuario);
    @Query("SELECT SUM(i.monto) FROM Ingreso i WHERE i.usuario = ?1 AND YEAR(i.fecha) = ?2 AND MONTH(i.fecha) = ?3")
    Double sumarIngresosMensuales(Usuario usuario, int anio, int mes);
    Optional<Ingreso> findByIdAndUsuario(UUID id, Usuario usuario);
    List<Ingreso> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate inicio, LocalDate fin);
}
