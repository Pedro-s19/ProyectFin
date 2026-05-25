package com.example.FinalProject.demo.Repository;

import com.example.FinalProject.demo.Model.Logro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LogroRepository extends JpaRepository<Logro, UUID> { }
