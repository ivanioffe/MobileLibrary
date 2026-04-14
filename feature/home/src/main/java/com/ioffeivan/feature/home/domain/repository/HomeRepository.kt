package com.ioffeivan.feature.home.domain.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.model.Books

internal interface HomeRepository {
    suspend fun getRecommendedBooks(): DataResult<Books, AppError>
}
