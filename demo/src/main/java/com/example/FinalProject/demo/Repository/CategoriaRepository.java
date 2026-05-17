package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.Categoria;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    List<Categoria> findByUsuarioOrEsPorDefectoTrue(Usuario usuario);

    @Query("SELECT c FROM Categoria c WHERE c.usuario = :usuario OR c.esPorDefecto = true")
    List<Categoria> findAllParaUsuario(@Param("usuario") Usuario usuario);

    boolean existsByNombreAndUsuario(String nombre, Usuario usuario);

    List<Categoria> findByUsuarioIsNullAndEsPorDefectoTrue();
}
