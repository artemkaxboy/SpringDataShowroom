package com.artemkaxboy.springdatashowroom.service

import com.artemkaxboy.springdatashowroom.configuration.property.ApplicationProperties
import com.artemkaxboy.springdatashowroom.dto.VersionDto
import org.springframework.stereotype.Service

@Service
class GeneralService(
    private val properties: ApplicationProperties,
) {

    fun getApplicationVersion(): VersionDto = VersionDto(properties.version, properties.revision)
}
