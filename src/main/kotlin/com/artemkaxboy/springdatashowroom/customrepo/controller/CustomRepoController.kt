package com.artemkaxboy.springdatashowroom.customrepo.controller

import com.artemkaxboy.springdatashowroom.configuration.API_V1
import com.artemkaxboy.springdatashowroom.customrepo.delegate.service.PersonCustom3Service
import com.artemkaxboy.springdatashowroom.customrepo.dto.PersonCustomDto
import com.artemkaxboy.springdatashowroom.customrepo.extensions.service.PersonCustom2Service
import com.artemkaxboy.springdatashowroom.customrepo.springstyle.service.PersonCustom1Service
import com.github.artemkaxboy.corelib.exceptions.ExceptionUtils.getMessage
import com.github.artemkaxboy.corelib.exceptions.getOrElse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

private const val UPDATE_RATING_ERROR = "Cannot update rating"

@RestController
@Validated
@RequestMapping(value = ["api/$API_V1/personCustom"])
@Tag(
    name = "Custom Repository",
    description = "Tag demonstrates how to customize repository"
)
class CustomRepoController(

    private val personCustom1Service: PersonCustom1Service,
    private val personCustom2Service: PersonCustom2Service,
    private val personCustom3Service: PersonCustom3Service,
    private val modelMapper: ModelMapper,
) {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleException(exception: Exception) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message, exception)
    }

    @GetMapping(
        "/custom1",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getPeopleCustom1(): List<PersonCustomDto> {

        return personCustom1Service.getAll()
            .map { modelMapper.map(it, PersonCustomDto::class.java) }
    }

    @PostMapping(
        "/custom1/generate",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun generatePersonCustom1(): PersonCustomDto {

        return modelMapper.map(personCustom1Service.generatePerson(), PersonCustomDto::class.java)
    }

    @PatchMapping(
        "/custom1/{personId}/rating",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateRating1(
        @Parameter(description = "Person's ID", example = "15")
        @PathVariable
        personId: Long,

        @Parameter(description = "New rating value", example = "250")
        @RequestParam
        rating: Int,
    ): PersonCustomDto {

        return personCustom1Service.updateRating(personId, rating)
            .getOrElse { throw IllegalArgumentException(it.getMessage(UPDATE_RATING_ERROR)) }
            .let { modelMapper.map(it, PersonCustomDto::class.java) }
    }

    @GetMapping(
        "/custom2",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getPeopleCustom2(): List<PersonCustomDto> {

        return personCustom2Service.getAll()
            .map { modelMapper.map(it, PersonCustomDto::class.java) }
    }

    @PostMapping(
        "/custom2/generate",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun generatePersonCustom2(): PersonCustomDto {

        return modelMapper.map(personCustom2Service.generatePerson(), PersonCustomDto::class.java)
    }

    @PatchMapping(
        "/custom2/{personId}/rating",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateRating2(
        @Parameter(description = "Person's ID", example = "15")
        @PathVariable
        personId: Long,

        @Parameter(description = "New rating value", example = "250")
        @RequestParam
        rating: Int,
    ): PersonCustomDto {

        return personCustom2Service.updateRating(personId, rating)
            .getOrElse { throw IllegalArgumentException(it.getMessage(UPDATE_RATING_ERROR)) }
            .let { modelMapper.map(it, PersonCustomDto::class.java) }
    }

    @GetMapping(
        "/custom3",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getPeopleCustom3(): List<PersonCustomDto> {

        return personCustom3Service.getAll()
            .map { modelMapper.map(it, PersonCustomDto::class.java) }
    }

    @PostMapping(
        "/custom3/generate",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun generatePersonCustom3(): PersonCustomDto {

        return modelMapper.map(personCustom3Service.generatePerson(), PersonCustomDto::class.java)
    }

    @PatchMapping(
        "/custom3/{personId}/rating",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateRating3(
        @Parameter(description = "Person's ID", example = "15")
        @PathVariable
        personId: Long,

        @Parameter(description = "New rating value", example = "250")
        @RequestParam
        rating: Int,
    ): PersonCustomDto {

        return personCustom3Service.updateRating(personId, rating)
            .getOrElse { throw IllegalArgumentException(it.getMessage(UPDATE_RATING_ERROR)) }
            .let { modelMapper.map(it, PersonCustomDto::class.java) }
    }
}
