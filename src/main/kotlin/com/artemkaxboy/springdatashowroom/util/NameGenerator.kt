package com.artemkaxboy.springdatashowroom.util

/**
 * Container for functions [generate].
 */
object NameGenerator {

    private val firsts = listOf(
        "Ivan",
        "Petr",
        "Sidor",
        "Andrey",
        "Denis",
        "Mihail",
        "Semen",
        "Viktor",
        "Vladimir",
        "Roman"
    )

    private val lasts = listOf(
        "Ivanov",
        "Petrov",
        "Sidorov",
        "Andreev",
        "Denisov",
        "Mihailov",
        "Semenov",
        "Viktorov",
        "Vladimirov",
        "Romanov"
    )

    /**
     * Generates a pair of first and last name. Uses set of 10 first and 10 last names, combining them randomly.
     */
    fun generate() = "${firsts.random()} ${lasts.random()}"
}
