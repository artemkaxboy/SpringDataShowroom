package com.artemkaxboy.springdatashowroom.util;

import com.artemkaxboy.springdatashowroom.lazy.kotlindata.Car2;
import com.artemkaxboy.springdatashowroom.lazy.kotlindata.Car2Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Car2Test {

    @Autowired
    private Car2Repository car2Repository;

    @Test
    void test2() {
        Car2 actual = car2Repository.findAll().stream().findFirst().orElse(null);
        assertThat(actual)
                .isNotNull();
//                .extracting(Car2::getBrand)
//                .isNotNull();
    }
}
