package com.artemkaxboy.springdatashowroom.customrepo.springstyle.service

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
internal class PersonCustom1ServiceTest {

    @Autowired
    private lateinit var personCustom1Service: PersonCustom1Service

    @AfterEach
    fun tearDown() {
        personCustom1Service.clear()
    }

    @Test
    fun `pass if generatePerson correctly`() {

        personCustom1Service.getAll()
            .also { Assertions.assertThat(it).isEmpty() }

        val expected = personCustom1Service.generatePerson()

        personCustom1Service.getAll()
            .also {
                Assertions.assertThat(it).hasSize(1)
                    .containsExactly(expected)
            }
    }

    @Test
    fun `pass if updates rating`() {

        val expectedRating = 157824
        val excepted = personCustom1Service.generatePerson().copy(rating = expectedRating)

        personCustom1Service.updateRating(excepted.id, expectedRating)
            .getOrElse { Assertions.fail("Result is failure") }
            .also { Assertions.assertThat(it).isEqualTo(excepted) }
    }

    @Test
    fun `pass if cannot update rating for unknown user`() {

        personCustom1Service.updateRating(-1, -1)
            .exceptionOrNull()
            .also {
                Assertions.assertThat(it).isNotNull
                    .hasMessageContaining("not found")
            }
    }

    @Test
    fun `pass if getAll returns all people`() {

        personCustom1Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }

        for (i in 1..10) {
            personCustom1Service.generatePerson()
            personCustom1Service.getAll().also { list ->
                Assertions.assertThat(list).hasSize(i)
            }
        }
    }

    @Test
    fun `pass if clear deletes all people`() {

        personCustom1Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }

        repeat((1..10).count()) {
            personCustom1Service.generatePerson()
        }
        personCustom1Service.getAll().also {
            Assertions.assertThat(it).isNotEmpty()
        }

        personCustom1Service.clear()
        personCustom1Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }
    }
}
