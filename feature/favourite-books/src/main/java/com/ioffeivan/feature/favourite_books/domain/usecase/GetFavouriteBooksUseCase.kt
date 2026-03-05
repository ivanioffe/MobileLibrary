package com.ioffeivan.feature.favourite_books.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import com.ioffeivan.core.model.Books
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class GetFavouriteBooksUseCase @Inject constructor(
    private val favouriteBooksRepository: FavouriteBooksRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Unit, GetFavouriteBooksUseCase.Success, GetFavouriteBooksUseCase.Error>(dispatcher) {
    sealed class Success {
        data class FavouriteBooks(val books: Books) : Success()
    }

    sealed class Error {
        data object NoFavouritesBooks : Error()
    }

    override suspend fun execute(parameters: Unit): Result<Success, Error> {
        val result = favouriteBooksRepository.getFavouriteBooks()

        return when (result) {
            is DataResult.Success -> {
                val books = result.data

                if (books.items.isNotEmpty()) {
                    Result.Success(Success.FavouriteBooks(result.data))
                } else {
                    Result.BusinessRuleError(Error.NoFavouritesBooks)
                }
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
