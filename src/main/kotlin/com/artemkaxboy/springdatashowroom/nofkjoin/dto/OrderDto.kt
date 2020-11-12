package com.artemkaxboy.springdatashowroom.nofkjoin.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Order information")
data class OrderDto(

    val account: String = "",

    val id: Long = -1,

    val sum: Double = Double.NaN,

    val comment: String = "",
)

@Schema(description = "New order information")
data class NewOrderDto(

    val sum: Double,

    val comment: String,
)
