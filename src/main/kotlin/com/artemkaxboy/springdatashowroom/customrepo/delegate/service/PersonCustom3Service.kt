package com.artemkaxboy.springdatashowroom.customrepo.delegate.service

import com.artemkaxboy.springdatashowroom.customrepo.delegate.entity.PersonCustom3
import com.artemkaxboy.springdatashowroom.customrepo.delegate.repository.PersonCustom3StorageService
import com.artemkaxboy.springdatashowroom.util.NameGenerator
import com.github.artemkaxboy.corelib.exceptions.Result
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PersonCustom3Service(
    private val personCustom3StorageService: PersonCustom3StorageService,
) {

    fun generatePerson(): PersonCustom3 {
        return personCustom3StorageService.save(PersonCustom3(name = NameGenerator.generate()))
    }

    fun updateRating(personId: Long, rating: Int): Result<PersonCustom3> {
        return personCustom3StorageService.updateRatingById(personId, rating)
            ?.let { Result.success(it) }
            ?: Result.failure("Person (id=$personId) not found")
    }

    fun getAll(): List<PersonCustom3> {
        return personCustom3StorageService.findAll(Sort.by(PersonCustom3::id.name))
    }
}
