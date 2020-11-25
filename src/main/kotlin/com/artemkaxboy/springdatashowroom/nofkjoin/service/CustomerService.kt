package com.artemkaxboy.springdatashowroom.nofkjoin.service

import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Customer
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Order
import com.artemkaxboy.springdatashowroom.nofkjoin.repository.CustomerRepository
import com.artemkaxboy.springdatashowroom.nofkjoin.repository.OrderRepository
import com.github.artemkaxboy.corelib.exceptions.ExceptionUtils.getMessage
import com.github.artemkaxboy.corelib.exceptions.Result
import com.github.artemkaxboy.corelib.exceptions.getOrElse
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Service provides high-level functions to work with [CustomerRepository] and [OrderRepository].
 */
@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
) {

    /**
     * Adds new customer to repository.
     */
    fun addCustomer(newCustomer: Customer): Result<Customer> {
        return validateNewCustomer(newCustomer)
            .getOrElse { return Result.failure(it) }
            .let { Result.success(customerRepository.save(it)) }
            .also { logger.trace { "Added new customer: ${it.getOrNull()}" } }
    }

    private fun validateNewCustomer(newCustomer: Customer): Result<Customer> {

        if (customerRepository.existsByAccount(newCustomer.account))
            return Result.failure(IllegalArgumentException("This account is already in use"))

        return Result.success(newCustomer)
    }

    private fun validateExistingCustomer(id: Long, existingCustomer: Customer): Result<Customer> {

        if (existingCustomer.id > 0 && existingCustomer.id != id)
            return Result.failure(IllegalArgumentException("Id cannot be updated"))

        if (!hasCustomer(id))
            return Result.failure(IllegalArgumentException("Unknown customer (id=$id)"))

        if (customerRepository.existsByIdNotAndAccount(id, existingCustomer.account))
            return Result.failure(IllegalArgumentException("This account is already in use"))

        return Result.success(existingCustomer)
    }

    /**
     * Updates customer with given [id].
     *
     * @param id of customer to update
     * @param customer updated customer data, customer's id must match [id] or be negative
     */
    fun updateCustomer(id: Long, customer: Customer): Result<Customer> {

        return validateExistingCustomer(id, customer)
            .getOrElse { return Result.failure(it) }
            .copy(id = id)
            .let { Result.success(customerRepository.save(it)) }
            .also { logger.trace { "Customer (id=$id) updated: ${it.getOrNull()}" } }
    }

    /**
     * Deletes customer with given [id].
     */
    @Transactional
    fun deleteCustomer(id: Long): Result<Unit> {

        getCustomer(id)
            .getOrElse { return Result.failure(it) }
            .also { deleteOrdersByAccount(it.account) }

        return Result.success(customerRepository.deleteById(id))
            .also { logger.trace { "Customer (id=$id) deleted" } }
    }

    /**
     * Finds all customers sorted by id.
     */
    fun getCustomers(): List<Customer> =
        customerRepository.findAll(Sort.by(Customer::id.name))

    /**
     * Finds customer with given [id].
     */
    fun getCustomer(id: Long): Result<Customer> =
        customerRepository.findByIdOrNull(id)?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("Unknown customer (id=$id)"))

    private fun hasCustomer(id: Long): Boolean = customerRepository.existsById(id)

    /**
     * Deletes all customers.
     */
    @Transactional
    fun deleteAllCustomers() {
        orderRepository.deleteAll()
        customerRepository.deleteAll()
            .also { logger.trace { "All customers deleted" } }
    }

    /**
     * Adds new order for given [customerId].
     */
    fun addOrder(customerId: Long, newOrder: Order): Result<Order> {

        return getCustomer(customerId)
            .getOrElse { return Result.failure(it.getMessage("Cannot add order")) }
            .let { newOrder.copy(account = it.account) }
            .let { Result.success(orderRepository.save(it)) }
            .also { logger.trace { "Added new order: $it" } }
    }

    /**
     * Deletes all orders associated with given [account].
     */
    fun deleteOrdersByAccount(account: String) =
        orderRepository.deleteAllByAccount(account)
            .also { logger.trace { "Orders for account ($account) deleted" } }

    /**
     * Finds all order by given [customerId].
     */
    fun getOrders(customerId: Long): Result<List<Order>> =
        getCustomer(customerId)
            .getOrElse { return Result.failure(it) }
            .let { Result.success(orderRepository.findAllByAccount(it.account)) }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
