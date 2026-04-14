package com.ioffeivan.feature.home.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto

internal interface HomeRemoteDataSource {
    suspend fun getRecommendedBooks(): DataResult<BooksDto, AppError>
}
