package com.artemkaxboy.springdatashowroom.customrepo.extensions.service

import com.github.artemkaxboy.corelib.exceptions.getOrElse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class PersonCustom2ServiceTest {

    @Autowired
    private lateinit var personCustom2Service: PersonCustom2Service

    @AfterEach
    fun tearDown() {
        personCustom2Service.clear()
    }

    @Test
    fun `pass if generatePerson correctly`() {

        personCustom2Service.getAll()
            .also { Assertions.assertThat(it).isEmpty() }

        val expected = personCustom2Service.generatePerson()

        personCustom2Service.getAll()
            .also {
                Assertions.assertThat(it).hasSize(1)
                    .containsExactly(expected)
            }
    }

    @Test
    fun `pass if updates rating`() {

        val expectedRating = 157824
        val excepted = personCustom2Service.generatePerson().copy(rating = expectedRating)

        personCustom2Service.updateRating(excepted.id, expectedRating)
            .getOrElse { Assertions.fail("Result is failure") }
            .also { Assertions.assertThat(it).isEqualTo(excepted) }
    }

    @Test
    fun `pass if cannot update rating for unknown user`() {

        personCustom2Service.updateRating(-1, -1)
            .exceptionOrNull()
            .also {
                Assertions.assertThat(it).isNotNull
                    .hasMessageContaining("not found")
            }
    }

    @Test
    fun `pass if getAll returns all people`() {

        personCustom2Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }

        for (i in 1..10) {
            personCustom2Service.generatePerson()
            personCustom2Service.getAll().also { list ->
                Assertions.assertThat(list).hasSize(i)
            }
        }
    }

    @Test
    fun `pass if clear deletes all people`() {

        personCustom2Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }

        repeat((1..10).count()) {
            personCustom2Service.generatePerson()
        }
        personCustom2Service.getAll().also {
            Assertions.assertThat(it).isNotEmpty()
        }

        personCustom2Service.clear()
        personCustom2Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }
    }
}
