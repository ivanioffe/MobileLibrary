package com.ioffeivan.feature.search.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto
import com.ioffeivan.feature.search.data.source.remote.api.SearchApiService
import com.ioffeivan.feature.search.domain.model.SearchParams
import javax.inject.Inject

internal class RetrofitSearchRemoteDataSource @Inject constructor(
    private val searchApiService: SearchApiService,
) : SearchRemoteDataSource {
    override suspend fun search(searchParams: SearchParams): DataResult<BooksDto, AppError> {
        return searchApiService.search(query = searchParams.query)
    }
}
