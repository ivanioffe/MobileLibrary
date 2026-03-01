package com.ioffeivan.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val error: ErrorDetailsDto,
)

@Serializable
data class ErrorDetailsDto(
    val code: Int,
    val message: String,
    val errors: List<ErrorItemDto>? = null,
)

@Serializable
data class ErrorItemDto(
    val message: String?,
    val domain: String?,
    val reason: String?,
    val location: String?,
    val locationType: String?,
)
