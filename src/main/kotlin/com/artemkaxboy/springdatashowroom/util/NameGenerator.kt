package com.artemkaxboy.springdatashowroom.util

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

    fun generate() = "${firsts.random()} ${lasts.random()}"
}
