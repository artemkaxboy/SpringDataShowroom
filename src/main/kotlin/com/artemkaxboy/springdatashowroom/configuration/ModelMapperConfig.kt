package com.artemkaxboy.springdatashowroom.configuration

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper(): ModelMapper {

        return ModelMapper().apply {
            // configuration.matchingStrategy = MatchingStrategies.STRICT
            configuration.isFieldMatchingEnabled = true
            // configuration.isSkipNullEnabled = true

            // necessary to work with kotlin
            configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        }
    }
}
