package com.ioffeivan.feature.search.data.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.data.mapper.toBooks
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.search.data.source.remote.data_source.SearchRemoteDataSource
import com.ioffeivan.feature.search.domain.model.SearchParams
import com.ioffeivan.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {
    override suspend fun search(searchParams: SearchParams): DataResult<Books, AppError> {
        val result = searchRemoteDataSource.search(searchParams)

        return when (result) {
            is DataResult.Success -> {
                DataResult.Success(result.data.toBooks())
            }

            is DataResult.Error -> {
                DataResult.Error(result.error)
            }
        }
    }
}
