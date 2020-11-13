package com.artemkaxboy.springdatashowroom.controller

import com.artemkaxboy.springdatashowroom.dto.VersionDto
import com.artemkaxboy.springdatashowroom.service.GeneralService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["api"])
@Tag(name = "General")
class GeneralController(
    private val generalService: GeneralService,
) {

    @GetMapping("/version", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getVersion(): VersionDto = generalService.getApplicationVersion()
}
