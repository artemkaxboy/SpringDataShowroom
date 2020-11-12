package com.artemkaxboy.springdatashowroom.nofkjoin.dto

import io.swagger.v3.oas.annotations.media.Schema

// ModelMapper mapping errors:
// 1) Failed to instantiate instance of destination com.**.NoFkJoinCustomerDto.
// Ensure that com.**.NoFkJoinCustomerDto has a non-private no-argument constructor.
// 1 error"
@Schema(description = "Customer information")
data class CustomerDto(

    val id: Long = -1,

    val account: String = "",

    val name: String = "",
)

@Schema(description = "New customer information")
data class NewCustomerDto(

    val account: String,

    val name: String,
)
