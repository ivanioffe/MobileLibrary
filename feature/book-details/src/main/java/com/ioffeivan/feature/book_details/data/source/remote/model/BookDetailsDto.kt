package com.ioffeivan.feature.book_details.data.source.remote.model

import com.ioffeivan.core.network.model.UserInfoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BookDetailsDto(
    val id: String,
    @SerialName("volumeInfo")
    val bookDetailsInfoDto: BookDetailsInfoDto,
    @SerialName("userInfo")
    val userInfoDto: UserInfoDto? = null,
)

@Serializable
data class BookDetailsInfoDto(
    val title: String,
    val authors: List<String>? = null,
    val description: String? = null,
    val imageLinks: BookDetailsImageLinks? = null,
)

@Serializable
data class BookDetailsImageLinks(
    val small: String?,
    val medium: String?,
    val large: String?,
    val extraLarge: String?,
)
