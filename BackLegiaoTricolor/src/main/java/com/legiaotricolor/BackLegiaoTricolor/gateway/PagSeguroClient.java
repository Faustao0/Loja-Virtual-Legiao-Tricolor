package com.legiaotricolor.BackLegiaoTricolor.gateway;

import com.legiaotricolor.BackLegiaoTricolor.dto.PagSeguroRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.PagSeguroResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagSeguroClient {

    private final WebClient webClient;

    @Value("${pagseguro.base-url}")
    private String baseUrl;

    @Value("${pagseguro.token}")
    private String token;

    public PagSeguroClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public PagSeguroResponseDTO createPayment(PagSeguroRequestDTO request) {

        return webClient.post()
                .uri(baseUrl + "/charges")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PagSeguroResponseDTO.class)
                .block(); // OK para backend síncrono
    }
}
