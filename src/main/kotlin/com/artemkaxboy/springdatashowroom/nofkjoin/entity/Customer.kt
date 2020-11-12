package com.artemkaxboy.springdatashowroom.nofkjoin.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "no_fk_join_customers")
//  https://stackoverflow.com/questions/9393234/mysql-unique-field-needs-to-be-an-index
// @Table(indexes = [Index(name = "my_index", columnList = "account", unique = false)])
data class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(unique = true)
    val account: String = "",

    val name: String = ""
) : Serializable {

    companion object {

        val DUMMY_CUSTOMER = Customer(account = "Dummy account", name = "Dummy name")
    }
}
