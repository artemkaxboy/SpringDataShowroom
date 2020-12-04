package com.artemkaxboy.springdatashowroom.customrepo.extensions.service

import com.artemkaxboy.springdatashowroom.customrepo.extensions.entity.PersonCustom2
import com.artemkaxboy.springdatashowroom.customrepo.extensions.repository.PersonCustom2Repository
import com.artemkaxboy.springdatashowroom.customrepo.extensions.repository.updateRatingById
import com.artemkaxboy.springdatashowroom.util.NameGenerator
import com.github.artemkaxboy.corelib.exceptions.Result
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PersonCustom2Service(
    private val personCustom2Repository: PersonCustom2Repository,
) {

    /**
     * Generates and saves person into repository.
     */
    fun generatePerson(): PersonCustom2 {
        return personCustom2Repository.save(PersonCustom2(name = NameGenerator.generate()))
    }

    /**
     * Updates rating for person with given [personId].
     */
    fun updateRating(personId: Long, rating: Int): Result<PersonCustom2> {
        return personCustom2Repository.updateRatingById(personId, rating)
            ?.let { Result.success(it) }
            ?: Result.failure("Person (id=$personId) not found")
    }

    /**
     * Returns all people from repository.
     */
    fun getAll(): List<PersonCustom2> {
        return personCustom2Repository.findAll(Sort.by(PersonCustom2::id.name))
    }

    /**
     * Deletes all people from repository.
     */
    fun clear() {
        personCustom2Repository.deleteAll()
    }
}
