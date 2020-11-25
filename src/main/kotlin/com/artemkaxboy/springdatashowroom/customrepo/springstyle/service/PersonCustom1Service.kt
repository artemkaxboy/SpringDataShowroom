package com.artemkaxboy.springdatashowroom.customrepo.springstyle.service

import com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity.PersonCustom1
import com.artemkaxboy.springdatashowroom.customrepo.springstyle.repository.PersonCustom1Repository
import com.artemkaxboy.springdatashowroom.util.NameGenerator
import com.github.artemkaxboy.corelib.exceptions.Result
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * Service demonstrates how to work with customized repository version Spring-style.
 */
@Service
class PersonCustom1Service(
    private val personCustom1Repository: PersonCustom1Repository,
) {

    /**
     * Generates and saves person into repository.
     */
    fun generatePerson(): PersonCustom1 {
        return personCustom1Repository.save(PersonCustom1(name = NameGenerator.generate()))
    }

    /**
     * Updates rating for person with given [personId].
     */
    fun updateRating(personId: Long, rating: Int): Result<PersonCustom1> {
        return personCustom1Repository.updateRatingById(personId, rating)
            ?.let { Result.success(it) }
            ?: Result.failure("Person (id=$personId) not found")
    }

    /**
     * Returns all people from repository.
     */
    fun getAll(): List<PersonCustom1> {
        return personCustom1Repository.findAll(Sort.by(PersonCustom1::id.name))
    }

    /**
     * Deletes all people from repository.
     */
    fun clear() {
        personCustom1Repository.deleteAll()
    }
}
