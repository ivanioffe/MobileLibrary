package com.ioffeivan.feature.search.domain.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.search.domain.model.SearchParams

internal interface SearchRepository {
    suspend fun search(searchParams: SearchParams): DataResult<Books, AppError>
}
