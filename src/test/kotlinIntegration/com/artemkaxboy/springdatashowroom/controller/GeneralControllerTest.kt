package com.artemkaxboy.springdatashowroom.controller

import com.artemkaxboy.springdatashowroom.dto.VersionDto
import com.artemkaxboy.springdatashowroom.expectJson200
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class GeneralControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `pass when getVersion works`() {

        webTestClient
            .get()
            .uri("/api/version")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectJson200()
            .expectBody<VersionDto>()
            .consumeWith {
                Assertions.assertThat(it.responseBody).isNotNull
            }
    }
}
