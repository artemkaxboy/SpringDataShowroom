package com.artemkaxboy.springdatashowroom.nofkjoin.repository

import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying

interface OrderRepository : JpaRepository<Order, Long> {

    fun findAllByAccount(account: String): List<Order>

    @Modifying
    fun deleteAllByAccount(account: String)
}
