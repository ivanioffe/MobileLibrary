package com.ioffeivan.feature.search.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.search.domain.model.SearchParams
import com.ioffeivan.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<SearchParams, SearchUseCase.Success, SearchUseCase.Error>(dispatcher) {
    sealed class Success {
        data class BooksData(val books: Books) : Success()
    }

    sealed class Error {
        data object NoBooksFound : Error()
    }

    override suspend fun execute(parameters: SearchParams): Result<Success, Error> {
        val result = searchRepository.search(parameters)

        return when (result) {
            is DataResult.Success -> {
                val books = result.data

                if (books.items.isNotEmpty()) {
                    Result.Success(Success.BooksData(books))
                } else {
                    Result.BusinessRuleError(Error.NoBooksFound)
                }
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
