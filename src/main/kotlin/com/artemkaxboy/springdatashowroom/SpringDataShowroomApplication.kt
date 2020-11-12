package com.artemkaxboy.springdatashowroom

import com.artemkaxboy.springdatashowroom.configuration.property.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class SpringDataShowroomApplication

fun main(args: Array<String>) {
    runApplication<SpringDataShowroomApplication>(*args)
}
