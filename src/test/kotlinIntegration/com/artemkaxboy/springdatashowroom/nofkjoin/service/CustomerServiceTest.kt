package com.artemkaxboy.springdatashowroom.nofkjoin.service

import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Customer
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Customer.Companion.DUMMY_CUSTOMER
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Order
import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Order.Companion.DUMMY_ORDER
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class CustomerServiceTest {

    @Autowired
    private lateinit var customerService: CustomerService

    @AfterEach
    fun clear() {
        customerService.deleteAllCustomers()
    }

    @Test
    fun `pass if customer is added`() {
        /* add customer */
        customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .isEqualToIgnoringGivenFields(DUMMY_CUSTOMER, Customer::id.name)
                    .matches { value -> value!!.id > 0 }
            }
    }

    @Test
    fun `pass if cannot add customer with existing account`() {
        /* add customer */
        customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }

        /* add customer with same account */
        customerService.addCustomer(DUMMY_CUSTOMER).exceptionOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasMessageContaining("already in use")
            }
    }

    @Test
    fun `pass if customer is updated`() {
        /* add customer */
        val updatedCustomer = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }
            .let { it!!.copy(account = "new account", name = "new name") }

        customerService.updateCustomer(updatedCustomer.id, updatedCustomer).getOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .isEqualTo(updatedCustomer)
            }
    }

    @Test
    fun `pass if cannot update customer's account to existing one`() {
        /* add customer */
        customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }

        val newCustomer2 = Customer(account = "account2", name = "name2")

        /* add another customer */
        val updatedCustomer = customerService.addCustomer(newCustomer2).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!
            .copy(account = DUMMY_CUSTOMER.account)

        customerService.updateCustomer(updatedCustomer.id, updatedCustomer).exceptionOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasMessageContaining("already in use")
            }
    }

    @Test
    fun `pass if cannot update customer's id`() {
        /* add customer */
        val insertedCustomer = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }

        val updatedCustomer = insertedCustomer.let { it!!.copy(id = it.id + 1) }

        customerService.updateCustomer(insertedCustomer!!.id, updatedCustomer).exceptionOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasMessage("Id cannot be updated")
            }
    }

    @Test
    fun `pass if cannot update unknown customer`() {
        customerService.updateCustomer(1, DUMMY_CUSTOMER).exceptionOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasMessageContaining("Unknown customer")
            }
    }

    @Test
    fun `pass if gets customers correctly works`() {
        Assertions.assertThat(customerService.getCustomers()).isEmpty()

        val addedCustomer1 = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }

        Assertions.assertThat(customerService.getCustomers())
            .hasSize(1)
            .containsExactly(addedCustomer1)

        val addedCustomer2 = customerService.addCustomer(DUMMY_CUSTOMER.copy(account = "account2")).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }

        Assertions.assertThat(customerService.getCustomers()).hasSize(2)
            .containsExactly(addedCustomer1, addedCustomer2)
    }

    @Test
    fun `pass if deletes customer correctly`() {
        val addedCustomer1 = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!

        customerService.deleteCustomer(addedCustomer1.id).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }
    }

    @Test
    fun `pass if cannot delete absent customer`() {
        customerService.deleteCustomer(1).exceptionOrNull()
            .also { Assertions.assertThat(it).isNotNull }
    }

    @Test
    fun `pass if orders are added`() {
        val addedCustomer = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!

        customerService.addOrder(addedCustomer.id, DUMMY_ORDER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!
    }

    @Test
    fun `pass if cannot add order for unknown customer`() {
        customerService.addOrder(123, DUMMY_ORDER).exceptionOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasMessageContaining("Unknown customer")
            }!!
    }

    @Test
    fun `pass if return orders correctly`() {
        val addedCustomerId = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!
            .id

        customerService.getOrders(addedCustomerId).getOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .isEmpty()
            }!!

        val addedOrder1 = customerService.addOrder(addedCustomerId, DUMMY_ORDER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!

        customerService.getOrders(addedCustomerId).getOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasSize(1)
                    .usingElementComparatorIgnoringFields(Order::customer.name)
                    .containsOnly(addedOrder1)
            }!!

        val addedOrder2 = customerService.addOrder(addedCustomerId, DUMMY_ORDER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!

        customerService.getOrders(addedCustomerId).getOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasSize(2)
                    .usingElementComparatorIgnoringFields(Order::customer.name)
                    .containsOnly(addedOrder1, addedOrder2)
            }!!
    }

    @Test
    fun `pass if cannot get orders for unknown customer`() {
        customerService.getOrders(-1).exceptionOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .hasMessageContaining("Unknown customer")
            }
    }

    @Test
    fun `pass if deletes related orders`() {
        /* add customer */
        val addedCustomerId = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!
            .id

        /* add order for customer */
        customerService.addOrder(addedCustomerId, DUMMY_ORDER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!

        /* delete the customer */
        customerService.deleteCustomer(addedCustomerId)

        /* add another customer with the same account */
        val anotherAddedCustomer = customerService.addCustomer(DUMMY_CUSTOMER).getOrNull()
            .also { Assertions.assertThat(it).isNotNull }!!
            .id

        customerService.getOrders(anotherAddedCustomer).getOrNull()
            .also {
                Assertions.assertThat(it)
                    .isNotNull
                    .isEmpty()
            }!!
    }
}
