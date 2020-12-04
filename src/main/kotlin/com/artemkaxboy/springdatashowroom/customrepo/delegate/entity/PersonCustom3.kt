package com.artemkaxboy.springdatashowroom.customrepo.delegate.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "person_custom_3")
data class PersonCustom3(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    val name: String = "",

    val rating: Int = 0,
)
