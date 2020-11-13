package com.artemkaxboy.springdatashowroom.customrepo.springstyle.repository

import com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity.PersonCustom1
import org.springframework.data.repository.findByIdOrNull

@Suppress("unused") // Spring is expecting to find it
// @link https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations
class PersonCustom1RepositoryCustomImpl(
    private val personCustom1Repository: PersonCustom1Repository
) : PersonCustom1RepositoryCustom {

    override fun updateRatingById(id: Long, rating: Int): PersonCustom1? {
        return personCustom1Repository.findByIdOrNull(id)
            ?.copy(rating = rating)
            ?.let { personCustom1Repository.save(it) }
    }
}
