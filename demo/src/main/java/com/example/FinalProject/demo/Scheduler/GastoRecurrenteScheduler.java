package com.example.FinalProject.demo.Scheduler;

import com.example.FinalProject.demo.Service.GastoRecurrenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class GastoRecurrenteScheduler {
    private final GastoRecurrenteService service;

    @Scheduled(cron = "0 0 8 * * ?")
    public void ejecutarGeneracion() {
        service.generarGastosDelDia();
    }
}
