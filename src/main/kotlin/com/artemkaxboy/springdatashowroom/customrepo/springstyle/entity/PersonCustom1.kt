package com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "person_custom_1")
data class PersonCustom1(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    val name: String = "",

    val rating: Int = 0,
)
