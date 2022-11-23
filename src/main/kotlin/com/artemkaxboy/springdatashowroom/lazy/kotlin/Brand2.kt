package com.artemkaxboy.springdatashowroom.lazy.kotlin

import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class Brand2 {
    @Id
    open var id: Long? = null
    open var name: String? = null
}
