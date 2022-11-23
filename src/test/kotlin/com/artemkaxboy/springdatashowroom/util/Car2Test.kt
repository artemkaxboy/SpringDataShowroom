package com.artemkaxboy.springdatashowroom.util

import com.artemkaxboy.springdatashowroom.lazy.kotlin.Car2Repository
import org.assertj.core.api.Assertions
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class Car2Test {

    @Autowired
    private lateinit var car2Repository: Car2Repository

    @Test
    fun failWhenLazyIsAccessible() {
        val actual = car2Repository.findAll().firstOrNull()

        Assertions.assertThat(actual)
            .isNotNull

        Assertions.assertThatThrownBy {
            actual!!.brand!!.name
        }.isInstanceOf(LazyInitializationException::class.java)
    }
}
