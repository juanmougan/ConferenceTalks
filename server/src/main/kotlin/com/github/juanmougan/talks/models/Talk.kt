package com.github.juanmougan.talks.models

import kotlinx.serialization.Serializable

@Serializable
data class Talk(
    val id: Long,
    val title: String,
    val abstract: String,
    val speakers: List<String>,
    val topics: List<String>,
    val room: String
)
