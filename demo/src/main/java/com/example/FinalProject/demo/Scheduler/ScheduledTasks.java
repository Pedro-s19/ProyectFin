package com.example.FinalProject.demo.Scheduler;

import com.example.FinalProject.demo.Service.Imp.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final ExchangeRateService exchangeRateService;

    @Scheduled(fixedRate = 14400000)
    public void actualizarTasasCambio() {
        log.info("Actualizando tasas de cambio programado...");
        exchangeRateService.actualizarTasas();
    }
}
