package com.example.FinalProject.demo.Util;

import com.example.FinalProject.demo.Service.Imp.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyConverter {
    private final ExchangeRateService exchangeRateService;

    public double convertir(double montoCOP, String monedaDestino) {
        if (monedaDestino == null || monedaDestino.equalsIgnoreCase("COP")) {
            return montoCOP;
        }
        double tasa = exchangeRateService.obtenerTasa("COP", monedaDestino);
        return montoCOP * tasa;
    }
}
