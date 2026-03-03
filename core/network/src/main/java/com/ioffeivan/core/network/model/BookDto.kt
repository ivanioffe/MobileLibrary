package com.ioffeivan.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnailUrl: String?,
)
