package com.artemkaxboy.springdatashowroom.nofkjoin.repository

import com.artemkaxboy.springdatashowroom.nofkjoin.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {

    fun existsByAccount(account: String): Boolean

    fun existsByIdNotAndAccount(id: Long, account: String): Boolean
}
