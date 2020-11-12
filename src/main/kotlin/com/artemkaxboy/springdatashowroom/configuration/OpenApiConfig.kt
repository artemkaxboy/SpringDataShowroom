package com.artemkaxboy.springdatashowroom.configuration

import com.artemkaxboy.springdatashowroom.configuration.property.ApplicationProperties
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val API_V1 = "v1"

const val CURRENT_API_VERSION = API_V1

@Configuration
class OpenApiConfig(
    private val applicationProperties: ApplicationProperties
) {

    @Bean
    fun customOpenAPI(): OpenAPI {

        return OpenAPI().info(
            Info().version(CURRENT_API_VERSION)
                .title(applicationProperties.name)
                .description(applicationProperties.description)
                .contact(
                    Contact().name(applicationProperties.contact.name)
                        .url(applicationProperties.contact.url)
                        .email(applicationProperties.contact.email)
                )
        )
    }
}
