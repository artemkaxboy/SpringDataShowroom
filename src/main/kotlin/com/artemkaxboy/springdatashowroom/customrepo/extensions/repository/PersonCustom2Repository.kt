package com.artemkaxboy.springdatashowroom.customrepo.extensions.repository

import com.artemkaxboy.springdatashowroom.customrepo.extensions.entity.PersonCustom2
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface PersonCustom2Repository : JpaRepository<PersonCustom2, Long>

fun PersonCustom2Repository.updateRatingById(id: Long, rating: Int): PersonCustom2? {
    return findByIdOrNull(id)
        ?.copy(rating = rating)
        ?.let { save(it) }
}
