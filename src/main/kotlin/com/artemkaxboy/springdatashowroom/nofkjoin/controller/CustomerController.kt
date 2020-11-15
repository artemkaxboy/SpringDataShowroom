package com.artemkaxboy.springdatashowroom.nofkjoin.controller

import com.artemkaxboy.springdatashowroom.configuration.API_V1
import com.artemkaxboy.springdatashowroom.nofkjoin.dto.CustomerDto
import com.artemkaxboy.springdatashowroom.nofkjoin.dto.NewCustomerDto
import com.artemkaxboy.springdatashowroom.nofkjoin.dto.NewOrderDto
import com.artemkaxboy.springdatashowroom.nofkjoin.dto.OrderDto
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Customer
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Order
import com.artemkaxboy.springdatashowroom.nofkjoin.service.CustomerService
import com.github.artemkaxboy.corelib.exceptions.ExceptionUtils.getMessage
import com.github.artemkaxboy.corelib.exceptions.getOrElse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@Validated
@RequestMapping(value = ["api/$API_V1"])
@Tag(
    name = "No FK join",
    description = "Tag demonstrates how to make join-able entities without adding foreign key"
)
class CustomerController(

    private val customerService: CustomerService,
    private val modelMapper: ModelMapper,
) {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleException(exception: Exception) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message, exception)
    }

    @PostMapping(
        "/customers",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun postCustomer(

        @RequestBody
        newCustomer: NewCustomerDto,
    ): CustomerDto {

        return modelMapper.map(newCustomer, Customer::class.java)
            .let { customerService.addCustomer(it) }
            .getOrElse { throw IllegalArgumentException(it.getMessage("Cannot create customer")) }
            .let { modelMapper.map(it, CustomerDto::class.java) }
    }

    @PutMapping(
        "/customers/{customerId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun putCustomer(
        @Parameter(description = "Customer's ID", example = "15")
        @PathVariable
        customerId: Long,

        @RequestBody
        customer: NewCustomerDto,
    ): CustomerDto {

        return modelMapper.map(customer, Customer::class.java)
            .let { customerService.updateCustomer(customerId, it) }
            .getOrElse { throw IllegalArgumentException(it.getMessage("Cannot update customer")) }
            .let { modelMapper.map(it, CustomerDto::class.java) }
    }

    @DeleteMapping("/customers/{customerId}")
    fun deleteCustomer(
        @Parameter(description = "Customer's ID", example = "15")
        @PathVariable
        customerId: Long,
    ) {

        KotlinLogging.logger { }

        customerService.deleteCustomer(customerId)
            .getOrElse { throw IllegalArgumentException(it.getMessage("Cannot delete customer")) }
    }

    @GetMapping("/customers", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCustomers(): List<CustomerDto> {
        return customerService.getCustomers()
            .map { modelMapper.map(it, CustomerDto::class.java) }
    }

    @GetMapping(
        "/customers/{customerId}/orders",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getOrders(
        @Parameter(description = "Customer's ID", example = "15")
        @PathVariable
        customerId: Long,
    ): List<OrderDto>? {

        return customerService.getOrders(customerId)
            .getOrElse { throw IllegalArgumentException(it.getMessage("Cannot get orders")) }
            .map { modelMapper.map(it, OrderDto::class.java) }
    }

    @PostMapping(
        "/customers/{customerId}/orders",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun postOrder(
        @Parameter(description = "Customer's ID", example = "15")
        @PathVariable
        customerId: Long,

        @RequestBody
        newOrder: NewOrderDto,
    ): OrderDto {

        return modelMapper.map(newOrder, Order::class.java)
            .let { customerService.addOrder(customerId, it) }
            .getOrElse { throw IllegalArgumentException(it.getMessage("Cannot add order")) }
            .let { modelMapper.map(it, OrderDto::class.java) }
    }
}
