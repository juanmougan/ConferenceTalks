package com.github.juanmougan.talks.services

import com.github.juanmougan.talks.helpers.readStrictCsv
import com.github.juanmougan.talks.models.Talk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import java.io.File
import java.time.LocalDateTime
import java.time.Month

class TalkService {

    companion object {
        val talks = flowOf(
            Talk(
                1L,
                "Kotlin rocks!",
                "Way better than Java :)",
                listOf("Mary", "Peter"),
                listOf("Programming"),
                "Room 1A - Aconcagua",
                startingTime = LocalDateTime.of(2024, Month.JANUARY, 10, 13, 30),
                endingTime = LocalDateTime.of(2024, Month.JANUARY, 10, 14, 30)
            ),
            Talk(
                2L,
                "Getting started with Multiplatform",
                "Some key concepts",
                listOf("Ashwin", "Jian Li"),
                listOf("Mobile"),
                "Room 2B - Chaltén",
                startingTime = LocalDateTime.of(2024, Month.MAY, 7, 9, 30),
                endingTime = LocalDateTime.of(2024, Month.JANUARY, 7, 11, 30)
            )
        )
    }

    fun getAll(): Flow<Talk> = talks

    suspend fun getById(id: Long) = talks.filter { it.id == id }.firstOrNull()

    fun bulkCreate(file: File): Flow<Talk> {
        val talks = readStrictCsv(file.inputStream())
        return talks.asFlow()
    }
}
