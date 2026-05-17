package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.MetaAhorro;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MetaAhorroRepository extends JpaRepository<MetaAhorro, UUID> {

    List<MetaAhorro> findByUsuario(Usuario usuario);
    Optional<MetaAhorro> findByIdAndUsuario(UUID id, Usuario usuario);
}
