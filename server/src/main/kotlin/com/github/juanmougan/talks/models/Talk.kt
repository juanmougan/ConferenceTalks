package com.github.juanmougan.talks.models

import com.github.juanmougan.talks.helpers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Talk(
    val id: Long,
    val title: String,
    val abstract: String,
    val speakers: List<String>,
    val topics: List<String>,
    val room: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startingTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endingTime: LocalDateTime,
)
