package com.pmdconsultancyrest.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {


    @Bean
    fun openApi(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Project Intake API")
                    .description("API for submitting project intake requests")
                    .version("v1")
            )
}