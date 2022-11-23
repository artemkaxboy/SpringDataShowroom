package com.artemkaxboy.springdatashowroom.util;

import javax.transaction.Transactional;

import com.artemkaxboy.springdatashowroom.lazy.java.Car1;
import com.artemkaxboy.springdatashowroom.lazy.java.Car1Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Car1Test {

    @Autowired
    private Car1Repository car1Repository;

    @Test
    @Transactional
    void test1() {
        Car1 actual = car1Repository.findAll().stream().findFirst().orElse(null);
        System.out.println(actual);
    }
}
