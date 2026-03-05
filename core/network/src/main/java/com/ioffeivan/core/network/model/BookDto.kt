package com.ioffeivan.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    @SerialName("volumeInfo")
    val bookInfoDto: BookInfoDto,
    @SerialName("userInfo")
    val userInfoDto: UserInfoDto? = null,
)

@Serializable
data class BookInfoDto(
    val title: String,
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null,
)

@Serializable
data class UserInfoDto(
    val updated: String,
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?,
)
