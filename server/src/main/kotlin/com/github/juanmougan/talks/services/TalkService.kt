package com.github.juanmougan.talks.services

import com.github.juanmougan.talks.models.Talk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

class TalkService {

    companion object {
        val talks = flowOf(
            Talk(
                1L,
                "Kotlin rocks!",
                "Way better than Java :)",
                listOf("Mary", "Peter"),
                listOf("Programming"),
                "Room 1A - Aconcagua"
            ),
            Talk(
                2L,
                "Getting started with Multiplatform",
                "Some key concepts",
                listOf("Ashwin", "Jian Li"),
                listOf("Mobile"),
                "Room 2B - Chaltén"
            )
        )
    }

    fun getAll(): Flow<Talk> = talks

    suspend fun getById(id: Long) = talks.filter { it.id == id }.firstOrNull()
}
