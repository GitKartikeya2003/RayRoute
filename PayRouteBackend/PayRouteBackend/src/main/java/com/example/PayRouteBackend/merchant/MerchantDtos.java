package com.example.PayRouteBackend.merchant;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MerchantDtos {


    public record Register(

            @NotBlank @Size(max = 100) String name,
            @Email @NotBlank @Size(max = 100) String email
    ) {
    }

    public record Update(
            String name,
            String email,
            String webhookUrl
    ) {
    }


    public record Registered(
            Merchant merchant, String apiKey, String webhookSecret
    ) {
    }

}
