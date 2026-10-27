package com.example.PayRouteBackend.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class OrderDtos {

    public record Create(
            @Min(100) @Max(100000) long amount, @NotBlank String receipt
    ) {

    }

}
