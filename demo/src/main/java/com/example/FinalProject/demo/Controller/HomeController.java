package com.example.FinalProject.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of(
                "status", "online",
                "app", "API Control de Gastos",
                "version", "1.0",
                "message", "El servidor está en línea y funcionando correctamente."
        ));
    }
}
