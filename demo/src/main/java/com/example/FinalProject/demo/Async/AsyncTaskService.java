package com.example.FinalProject.demo.Async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncTaskService {
    @Async("taskExecutor")
    public void procesarSincronizacionEnSegundoPlano(String usuarioId) {
        log.info("Procesando sincronización offline para usuario: {}", usuarioId);
    }
}
