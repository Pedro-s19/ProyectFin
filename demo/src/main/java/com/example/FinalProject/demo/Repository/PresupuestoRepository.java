package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.PresupuestoMensual;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PresupuestoRepository extends JpaRepository<PresupuestoMensual, UUID> {

    Optional<PresupuestoMensual> findByUsuarioAndCategoriaAndAnioAndMes(Usuario usuario, Categoria categoria, int anio, int mes);
    List<PresupuestoMensual> findByUsuarioAndAnioAndMes(Usuario usuario, int anio, int mes);
}
