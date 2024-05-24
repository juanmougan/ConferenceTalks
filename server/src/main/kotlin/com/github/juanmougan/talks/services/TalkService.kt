package com.github.juanmougan.talks.services

import com.github.juanmougan.talks.models.Talk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TalkService {
    fun getAll(): Flow<Talk> = flowOf(
        Talk(
            1L,
            "Kotlin rocks!",
            "Way better than Java :)",
            listOf("Mary", "Peter"),
            listOf("Programming")
        ),
        Talk(
            2L,
            "Getting started with Multiplatform",
            "Some key concepts",
            listOf("Ashwin", "Jian Li"),
            listOf("Mobile")
        )
    )
}
