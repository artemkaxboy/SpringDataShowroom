package com.artemkaxboy.springdatashowroom.customrepo.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Custom person information")
data class PersonCustomDto(

    val id: Long = -1,

    val name: String = "",

    val rating: Int = 0,
)
