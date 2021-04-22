package com.artemkaxboy.springdatashowroom.customrepo.delegate.service

import com.artemkaxboy.springdatashowroom.customrepo.delegate.entity.PersonCustom3
import com.artemkaxboy.springdatashowroom.customrepo.delegate.repository.PersonCustom3Repository
import com.artemkaxboy.springdatashowroom.util.NameGenerator
import com.github.artemkaxboy.corelib.exceptions.Result
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PersonCustom3Service(
    private val personCustom3Repository: PersonCustom3Repository,
) {

    /**
     * Generates and saves person into repository.
     */
    fun generatePerson(): PersonCustom3 {
        return personCustom3Repository.save(PersonCustom3(name = NameGenerator.generate()))
    }

    /**
     * Updates rating for person with given [personId].
     */
    fun updateRating(personId: Long, rating: Int): Result<PersonCustom3> {
        return personCustom3Repository.updateRatingById(personId, rating)
            ?.let { Result.success(it) }
            ?: Result.failure("Person (id=$personId) not found")
    }

    /**
     * Returns all people from repository.
     */
    fun getAll(): List<PersonCustom3> {
        return personCustom3Repository.findAll(Sort.by(PersonCustom3::id.name))
    }

    /**
     * Deletes all people from repository.
     */
    fun clear() {
        personCustom3Repository.deleteAll()
    }
}
