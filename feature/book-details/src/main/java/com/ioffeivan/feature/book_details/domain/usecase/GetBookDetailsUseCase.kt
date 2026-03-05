package com.ioffeivan.feature.book_details.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.domain.usecase.utils.BookId
import com.ioffeivan.feature.book_details.domain.model.BookDetails
import com.ioffeivan.feature.book_details.domain.repository.BookDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class GetBookDetailsUseCase @Inject constructor(
    private val getBookDetailsRepository: BookDetailsRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<BookId, GetBookDetailsUseCase.Success, Nothing>(dispatcher) {
    sealed class Success {
        data class BookDetailsData(val bookDetails: BookDetails) : Success()
    }

    override suspend fun execute(parameters: BookId): Result<Success, Nothing> {
        val result = getBookDetailsRepository.getBookDetails(parameters)

        return when (result) {
            is DataResult.Success -> {
                Result.Success(Success.BookDetailsData(result.data))
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
