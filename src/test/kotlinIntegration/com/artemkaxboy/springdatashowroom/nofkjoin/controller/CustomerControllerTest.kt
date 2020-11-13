package com.artemkaxboy.springdatashowroom.nofkjoin.controller

import com.artemkaxboy.springdatashowroom.TestConstants.BASE_URL
import com.artemkaxboy.springdatashowroom.expectJson
import com.artemkaxboy.springdatashowroom.expectJson200
import com.artemkaxboy.springdatashowroom.nofkjoin.dto.CustomerDto
import com.artemkaxboy.springdatashowroom.nofkjoin.dto.NewCustomerDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@Disabled
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class CustomerControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `pass when post new customer`() {
        val newCustomer = NewCustomerDto(account = "account", name = "name")

        webTestClient
            // .mutate()
            // .responseTimeout(Duration.ofMinutes(5)) // @example timeout for webClient
            // .build()
            .post()
            .uri("$BASE_URL/customer")
            .bodyValue(newCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()
            .expectBody<CustomerDto>()
            .consumeWith {
                Assertions.assertThat(it.responseBody).isNotNull
                    .isEqualToIgnoringGivenFields(newCustomer, CustomerDto::id.name)
            }
    }

    @Test
    fun `fail when post new customer with existing account`() {
        val newCustomer = NewCustomerDto(account = "account", name = "name")

        webTestClient
            .post()
            .uri("$BASE_URL/customer")
            .bodyValue(newCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()

        webTestClient
            .post()
            .uri("$BASE_URL/customer")
            .bodyValue(newCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest
    }

    @Test
    fun `pass when update customer`() {
        val newCustomer = NewCustomerDto(account = "account", name = "name")

        val savedCustomer = webTestClient
            .post()
            .uri("$BASE_URL/customer")
            .bodyValue(newCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()
            .expectBody<CustomerDto>()
            .returnResult()
            .responseBody

        Assertions.assertThat(savedCustomer).isNotNull
        val changedCustomer = savedCustomer!!.copy(account = "account2", name = "name2")

        webTestClient
            .put()
            .uri("$BASE_URL/customer/${changedCustomer.id}")
            .bodyValue(changedCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()
            .expectBody<CustomerDto>()
            .consumeWith {
                Assertions.assertThat(it.responseBody).isNotNull
                    .isEqualTo(changedCustomer)
            }
    }

    @Test
    fun `fail when update account to existing one`() {
        val customer1 = NewCustomerDto("account1", "customer1")

        webTestClient
            .post()
            .uri("$BASE_URL/customer")
            .bodyValue(customer1)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()

        val customer2 = NewCustomerDto("account2", "customer2")
        val savedCustomer2 = webTestClient
            .post()
            .uri("$BASE_URL/customer")
            .bodyValue(customer2)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()
            .expectBody<CustomerDto>()
            .returnResult()
            .responseBody

        Assertions.assertThat(savedCustomer2).isNotNull
        val changedCustomer = savedCustomer2!!.copy(account = customer1.account)

        webTestClient
            .put()
            .uri("$BASE_URL/customer/${changedCustomer.id}")
            .bodyValue(changedCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.message")
            .value<String> {
                Assertions.assertThat(it).contains("already in use")
            }
    }

    @Test
    fun `pass when cannot delete absent customer`() {
        webTestClient
            .delete()
            .uri("$BASE_URL/customer/999000")
            .exchange()
            .expectJson(HttpStatus.BAD_REQUEST)
    }
}
