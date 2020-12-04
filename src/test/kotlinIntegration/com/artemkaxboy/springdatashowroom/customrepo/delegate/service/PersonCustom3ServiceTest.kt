package com.artemkaxboy.springdatashowroom.customrepo.delegate.service

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
internal class PersonCustom3ServiceTest {

    @Autowired
    private lateinit var personCustom3Service: PersonCustom3Service

    @AfterEach
    fun tearDown() {
        personCustom3Service.clear()
    }

    @Test
    fun `pass if generatePerson correctly`() {

        personCustom3Service.getAll()
            .also { Assertions.assertThat(it).isEmpty() }

        val expected = personCustom3Service.generatePerson()

        personCustom3Service.getAll()
            .also {
                Assertions.assertThat(it).hasSize(1)
                    .containsExactly(expected)
            }
    }

    @Test
    fun `pass if updates rating`() {

        val expectedRating = 157824
        val excepted = personCustom3Service.generatePerson().copy(rating = expectedRating)

        personCustom3Service.updateRating(excepted.id, expectedRating)
            .getOrElse { Assertions.fail("Result is failure") }
            .also { Assertions.assertThat(it).isEqualTo(excepted) }
    }

    @Test
    fun `pass if cannot update rating for unknown user`() {

        personCustom3Service.updateRating(-1, -1)
            .exceptionOrNull()
            .also {
                Assertions.assertThat(it).isNotNull
                    .hasMessageContaining("not found")
            }
    }

    @Test
    fun `pass if getAll returns all people`() {

        personCustom3Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }

        for (i in 1..10) {
            personCustom3Service.generatePerson()
            personCustom3Service.getAll().also { list ->
                Assertions.assertThat(list).hasSize(i)
            }
        }
    }

    @Test
    fun `pass if clear deletes all people`() {

        personCustom3Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }

        repeat((1..10).count()) {
            personCustom3Service.generatePerson()
        }
        personCustom3Service.getAll().also {
            Assertions.assertThat(it).isNotEmpty()
        }

        personCustom3Service.clear()
        personCustom3Service.getAll().also {
            Assertions.assertThat(it).isEmpty()
        }
    }
}
