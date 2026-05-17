package com.example.FinalProject.demo.Cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheService {

    @CacheEvict(value = "tasasCambio", allEntries = true)
    @Scheduled(fixedDelay = 14400000)
    public void limpiarCacheTasas() {
        System.out.println("Cache de tasas limpiado");
    }
}
