package com.ioffeivan.feature.home.data.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.data.mapper.toBooks
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.home.data.source.remote.data_source.HomeRemoteDataSource
import com.ioffeivan.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {
    override suspend fun getRecommendedBooks(): DataResult<Books, AppError> {
        val result = homeRemoteDataSource.getRecommendedBooks()

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
