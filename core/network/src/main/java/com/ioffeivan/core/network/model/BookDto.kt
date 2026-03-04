package com.ioffeivan.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    @SerialName("volumeInfo")
    val bookInfo: BookInfo,
)

@Serializable
data class BookInfo(
    val title: String,
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null,
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?,
)
