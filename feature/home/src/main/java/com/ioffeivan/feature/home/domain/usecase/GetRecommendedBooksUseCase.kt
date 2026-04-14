package com.ioffeivan.feature.home.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class GetRecommendedBooksUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Unit, GetRecommendedBooksUseCase.Success, GetRecommendedBooksUseCase.Error>(dispatcher) {
    sealed class Success {
        data class RecommendedBooks(val books: Books) : Success()
    }

    sealed class Error {
        data object NoRecommendedBooks : Error()
    }

    override suspend fun execute(parameters: Unit): Result<Success, Error> {
        val result = homeRepository.getRecommendedBooks()

        return when (result) {
            is DataResult.Success -> {
                val recommendedBooks = result.data

                if (recommendedBooks.items.isNotEmpty()) {
                    Result.Success(Success.RecommendedBooks(result.data))
                } else {
                    Result.BusinessRuleError(Error.NoRecommendedBooks)
                }
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
