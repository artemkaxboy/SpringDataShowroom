package com.artemkaxboy.springdatashowroom.nofkjoin.service

import com.artemkaxboy.kotlin.corelib.exceptions.ExceptionUtils.getMessage
import com.artemkaxboy.kotlin.corelib.exceptions.Result
import com.artemkaxboy.kotlin.corelib.exceptions.getOrElse
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Customer
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Order
import com.artemkaxboy.springdatashowroom.nofkjoin.repository.CustomerRepository
import com.artemkaxboy.springdatashowroom.nofkjoin.repository.OrderRepository
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
) {

    fun addCustomer(newCustomer: Customer): Result<Customer> {
        return validateNewCustomer(newCustomer)
            .getOrElse { return Result.failure(it) }
            .let { Result.success(customerRepository.save(it)) }
            .also { logger.trace { "Added new customer: ${it.getOrNull()}" } }
    }

    fun validateNewCustomer(newCustomer: Customer): Result<Customer> {

        if (customerRepository.existsByAccount(newCustomer.account))
            return Result.failure(IllegalArgumentException("This account is already in use"))

        return Result.success(newCustomer)
    }

    fun validateExistingCustomer(id: Long, newCustomer: Customer): Result<Customer> {

        if (newCustomer.id > 0 && newCustomer.id != id)
            return Result.failure(IllegalArgumentException("Id cannot be updated"))

        if (!hasCustomer(id))
            return Result.failure(IllegalArgumentException("Unknown customer (id=$id)"))

        if (customerRepository.existsByIdNotAndAccount(id, newCustomer.account))
            return Result.failure(IllegalArgumentException("This account is already in use"))

        return Result.success(newCustomer)
    }

    fun updateCustomer(id: Long, customer: Customer): Result<Customer> {

        return validateExistingCustomer(id, customer)
            .getOrElse { return Result.failure(it) }
            .copy(id = id)
            .let { Result.success(customerRepository.save(it)) }
            .also { logger.trace { "Customer (id=$id) updated: ${it.getOrNull()}" } }
    }

    @Transactional
    fun deleteCustomer(id: Long): Result<Unit> {

        getCustomer(id)
            .getOrElse { return Result.failure(it) }
            .also { deleteOrdersByAccount(it.account) }

        return Result.success(customerRepository.deleteById(id))
            .also { logger.trace { "Customer (id=$id) deleted" } }
    }

    fun getCustomers(): List<Customer> =
        customerRepository.findAll(Sort.by(Customer::id.name))

    fun getCustomer(id: Long): Result<Customer> =
        customerRepository.findByIdOrNull(id)?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("Unknown customer (id=$id)"))

    fun hasCustomer(id: Long): Boolean = customerRepository.existsById(id)

    @Transactional
    fun deleteAllCustomers() {
        orderRepository.deleteAll()
        customerRepository.deleteAll()
            .also { logger.trace { "All customers deleted" } }
    }

    fun addOrder(customerId: Long, newOrder: Order): Result<Order> {

        return getCustomer(customerId)
            .getOrElse { return Result.failure(it.getMessage("Cannot add order")) }
            .let { newOrder.copy(account = it.account) }
            .let { Result.success(orderRepository.save(it)) }
            .also { logger.trace { "Added new order: $it" } }
    }

    fun deleteOrdersByAccount(account: String) =
        orderRepository.deleteAllByAccount(account)
            .also { logger.trace { "Orders for account ($account) deleted" } }

    fun getOrders(customerId: Long): Result<List<Order>> =
        getCustomer(customerId)
            .getOrElse { return Result.failure(it) }
            .let { Result.success(orderRepository.findAllByAccount(it.account)) }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
