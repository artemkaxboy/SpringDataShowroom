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

    fun generatePerson(): PersonCustom2 {
        return personCustom2Repository.save(PersonCustom2(name = NameGenerator.generate()))
    }

    fun updateRating(personId: Long, rating: Int): Result<PersonCustom2> {
        return personCustom2Repository.updateRatingById(personId, rating)
            ?.let { Result.success(it) }
            ?: Result.failure("Person (id=$personId) not found")
    }

    fun getAll(): List<PersonCustom2> {
        return personCustom2Repository.findAll(Sort.by(PersonCustom2::id.name))
    }
}
