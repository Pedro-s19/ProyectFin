package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.GastoRecurrente;
import com.example.FinalProject.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GastoRecurrenteRepository extends JpaRepository<GastoRecurrente, UUID> {
    List<GastoRecurrente> findByUsuarioAndActivoTrue(Usuario usuario);
    List<GastoRecurrente> findByDiaMesAndActivoTrue(int diaMes);
}
