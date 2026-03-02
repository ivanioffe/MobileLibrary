package com.ioffeivan.feature.search.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.search.domain.model.SearchParams
import com.ioffeivan.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class SearchUseCase(
    private val searchRepository: SearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<SearchParams, SearchUseCase.SearchSuccess, SearchUseCase.SearchErrors>(dispatcher) {
    sealed class SearchSuccess {
        data class SearchData(val books: Books) : SearchSuccess()
    }

    sealed class SearchErrors {
        data object NoSearchData : SearchErrors()
    }

    override suspend fun execute(parameters: SearchParams): Result<SearchSuccess, SearchErrors> {
        val result = searchRepository.search(parameters)

        return when (result) {
            is DataResult.Success -> {
                val books = result.data

                if (books.items.isNotEmpty()) {
                    Result.Success(SearchSuccess.SearchData(books))
                } else {
                    Result.BusinessRuleError(SearchErrors.NoSearchData)
                }
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
