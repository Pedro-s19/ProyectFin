package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.UsuarioLogro;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface UsuarioLogroRepository extends JpaRepository<UsuarioLogro, UUID> {
    List<UsuarioLogro> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndLogroId(Usuario usuario, UUID logroId);
}
