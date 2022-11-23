package com.artemkaxboy.springdatashowroom.lazy.kotlindata

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Brand3 (
    @Id
    var id: Long? = null,
    var name: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Brand3

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}
