package com.ioffeivan.feature.home.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto
import com.ioffeivan.feature.home.data.source.remote.api.HomeApiService
import javax.inject.Inject

internal class RetrofitHomeRemoteDataSource @Inject constructor(
    private val homeApiService: HomeApiService,
) : HomeRemoteDataSource {
    override suspend fun getRecommendedBooks(): DataResult<BooksDto, AppError> {
        return homeApiService.getRecommendedBooks()
    }
}
