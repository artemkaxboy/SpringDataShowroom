package com.artemkaxboy.springdatashowroom.customrepo.delegate.repository

import com.artemkaxboy.springdatashowroom.customrepo.delegate.entity.PersonCustom3
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PersonCustom3StorageService(

    val personCustom3Repository: PersonCustom3Repository
) : PersonCustom3Repository by personCustom3Repository {

    fun updateRatingById(id: Long, rating: Int): PersonCustom3? {
        return findByIdOrNull(id)
            ?.copy(rating = rating)
            ?.let { save(it) }
    }
}
