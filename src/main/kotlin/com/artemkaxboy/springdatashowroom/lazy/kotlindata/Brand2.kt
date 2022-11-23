package com.artemkaxboy.springdatashowroom.lazy.kotlindata

import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class Brand2 {
    @Id
    var id: Long? = null
        private set
    var name: String? = null
        private set

    constructor() {}
    constructor(id: Long?, name: String?) {
        this.id = id
        this.name = name
    }
}
