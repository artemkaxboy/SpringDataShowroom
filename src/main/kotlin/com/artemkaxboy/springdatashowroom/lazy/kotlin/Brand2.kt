package com.artemkaxboy.springdatashowroom.lazy.kotlin

import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class Brand2 {
    @Id
    var id: Long? = null
    var name: String? = null
}
