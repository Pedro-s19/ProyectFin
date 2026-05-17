package com.example.FinalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AporteMetaRequest {

    @NotNull @Positive
    private Double cantidad;
}
