package com.artemkaxboy.springdatashowroom.nofkjoin.entity

import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "no_fk_join_orders")
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    val sum: Double = Double.NaN,

    val comment: String = "",

    val account: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "account",
        referencedColumnName = "account",
        updatable = false,
        insertable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val customer: Customer? = null
) {

    companion object {

        val DUMMY_ORDER = Order(sum = 123.5, comment = "dummy order", account = "dummy order account")
    }
}
