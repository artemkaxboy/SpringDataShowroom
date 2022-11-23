package com.artemkaxboy.springdatashowroom.lazy.kotlin

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.StringJoiner
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
open class Car2 {

    @Id
    private var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "brand_id")
    open var brand: Brand2? = null

    @Column(name = "brand_id", insertable = false, updatable = false)
    private val brandId: Long? = null

    private var model: String? = null

    override fun toString(): String {
        return StringJoiner(", ", Car2::class.java.simpleName + "[", "]")
            .add("id=$id")
            .add("brandId=$brandId")
            .add("model='$model'")
            .toString()
    }
}
