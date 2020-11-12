package com.artemkaxboy.springdatashowroom.dto

import io.swagger.v3.oas.annotations.media.Schema

@Suppress("unused")
@Schema(title = "Version", description = "Basic version information.")
class VersionDto (

    val version: String,
)
