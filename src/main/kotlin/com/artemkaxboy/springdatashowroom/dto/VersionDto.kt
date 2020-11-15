package com.artemkaxboy.springdatashowroom.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "Version", description = "Basic version information.")
data class VersionDto(

    val version: String = "",
)
