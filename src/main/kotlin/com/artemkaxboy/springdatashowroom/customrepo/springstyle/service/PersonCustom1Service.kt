package com.artemkaxboy.springdatashowroom.customrepo.springstyle.service

import com.artemkaxboy.kotlin.corelib.exceptions.Result
import com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity.PersonCustom1
import com.artemkaxboy.springdatashowroom.customrepo.springstyle.repository.PersonCustom1Repository
import com.artemkaxboy.springdatashowroom.util.NameGenerator
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PersonCustom1Service(
    private val personCustom1Repository: PersonCustom1Repository,
) {

    fun generatePerson(): PersonCustom1 {
        return personCustom1Repository.save(PersonCustom1(name = NameGenerator.generate()))
    }

    fun updateRating(personId: Long, rating: Int): Result<PersonCustom1> {
        return personCustom1Repository.updateRatingById(personId, rating)
            ?.let { Result.success(it) }
            ?: Result.failure("Person (id=$personId) not found")
    }

    fun getAll(): List<PersonCustom1> {
        return personCustom1Repository.findAll(Sort.by(PersonCustom1::id.name))
    }
}
