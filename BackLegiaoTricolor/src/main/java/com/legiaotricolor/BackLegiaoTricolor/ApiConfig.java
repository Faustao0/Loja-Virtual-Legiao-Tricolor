package com.legiaotricolor.BackLegiaoTricolor;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Loja Tricolor")
                        .version("1.0.0")
                        .description("Documentação gerada com Swagger e Springdoc OpenAPI"));
    }
}
