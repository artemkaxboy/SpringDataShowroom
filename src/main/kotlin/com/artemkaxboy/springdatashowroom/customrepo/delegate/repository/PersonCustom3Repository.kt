package com.artemkaxboy.springdatashowroom.customrepo.delegate.repository

import com.artemkaxboy.springdatashowroom.customrepo.delegate.entity.PersonCustom3
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface PersonCustom3RepositoryI : JpaRepository<PersonCustom3, Long>

@Repository
class PersonCustom3Repository(

    personCustom3RepositoryI: PersonCustom3RepositoryI
) : PersonCustom3RepositoryI by personCustom3RepositoryI {

    fun updateRatingById(id: Long, rating: Int): PersonCustom3? {
        return findByIdOrNull(id)
            ?.copy(rating = rating)
            ?.let { save(it) }
    }
}
