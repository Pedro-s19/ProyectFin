package com.example.FinalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class AlertaResponse {
    private List<String> alertas;
}
