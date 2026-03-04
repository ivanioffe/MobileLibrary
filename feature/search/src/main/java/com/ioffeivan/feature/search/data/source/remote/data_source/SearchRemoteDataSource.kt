package com.ioffeivan.feature.search.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto
import com.ioffeivan.feature.search.domain.model.SearchParams

internal interface SearchRemoteDataSource {
    suspend fun search(searchParams: SearchParams): DataResult<BooksDto, AppError>
}
