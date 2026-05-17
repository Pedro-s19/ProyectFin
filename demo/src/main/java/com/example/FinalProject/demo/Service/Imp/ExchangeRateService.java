package com.example.FinalProject.demo.Service.Imp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ExchangeRateService {

    // ExchangeRate-API soporta COP directamente como base (gratuita, sin API key)
    private static final String API_URL = "https://open.er-api.com/v6/latest/COP";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Cache<String, Map<String, Double>> cache;

    @PostConstruct
    public void init() {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.HOURS)
                .maximumSize(1)
                .build();
        actualizarTasas();
    }

    public void actualizarTasas() {
        try {
            log.info("Obteniendo tasas de cambio desde API...");
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode root = objectMapper.readTree(response);

            // La API retorna: { "result": "success", "base_code": "COP", "rates": { "USD": 0.00025, ... } }
            if (!"success".equals(root.path("result").asText())) {
                log.warn("Respuesta inválida de la API de tasas: {}", root.path("result").asText());
                return;
            }

            JsonNode ratesNode = root.get("rates");
            Map<String, Double> rates = new HashMap<>();
            ratesNode.fields().forEachRemaining(e ->
                    rates.put(e.getKey(), e.getValue().asDouble()));

            cache.put("rates", rates);
            log.info("Tasas actualizadas correctamente. {} monedas cargadas.", rates.size());

        } catch (Exception e) {
            log.error("Error actualizando tasas", e);
        }
    }

    public double obtenerTasa(String fromCurrency, String toCurrency) {
        if (!"COP".equalsIgnoreCase(fromCurrency)) {
            throw new IllegalArgumentException("La moneda base debe ser COP");
        }
        Map<String, Double> rates = cache.getIfPresent("rates");
        if (rates == null) {
            actualizarTasas();
            rates = cache.getIfPresent("rates");
            if (rates == null) throw new RuntimeException("No se pudieron obtener las tasas");
        }
        Double rate = rates.get(toCurrency.toUpperCase());
        if (rate == null) throw new IllegalArgumentException("Moneda no soportada: " + toCurrency);
        return rate;
    }
}
