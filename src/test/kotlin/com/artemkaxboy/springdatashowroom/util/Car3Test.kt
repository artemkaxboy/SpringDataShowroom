package com.artemkaxboy.springdatashowroom.util

import com.artemkaxboy.springdatashowroom.lazy.kotlindata.Car3Repository
import org.assertj.core.api.Assertions
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
internal class Car3Test {

    @Autowired
    private lateinit var car3Repository: Car3Repository

    @Test
    fun failWhenLazyIsAccessible() {
        val actual = car3Repository.findAll().firstOrNull()

        Assertions.assertThat(actual)
            .isNotNull

        Assertions.assertThatThrownBy {
            actual!!.brand!!.name
        }.isInstanceOf(LazyInitializationException::class.java)
    }

    @Test
    @Transactional
    fun passWhenLazyAccessibleInSession() {
        val actual = car3Repository.findAll().firstOrNull()

        Assertions.assertThat(actual)
            .isNotNull

        Assertions.assertThat(actual!!.brand!!.name).isNotBlank
    }
}
