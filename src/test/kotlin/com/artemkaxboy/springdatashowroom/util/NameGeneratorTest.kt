package com.artemkaxboy.springdatashowroom.util

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class NameGeneratorTest {

    @Test
    fun `pass if generates different names`() {

        val expectedAtLeast = 8
        val attempts = 10

        val actual = (1..attempts)
            .map { NameGenerator.generate() }
            .distinct()
            .count()

        Assertions.assertThat(actual).isGreaterThanOrEqualTo(expectedAtLeast)
    }
}
