package com.ioffeivan.feature.favourite_books.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.domain.base.FlowUseCase
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import com.ioffeivan.core.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ObserveFavouriteBooksUseCase @Inject constructor(
    private val favouriteBooksRepository: FavouriteBooksRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, ObserveFavouriteBooksUseCase.Success, ObserveFavouriteBooksUseCase.Error>(
        dispatcher,
    ) {
    sealed class Success {
        data class FavouriteBooks(val books: List<Book>) : Success()
    }

    sealed class Error {
        data object NoFavouritesBooks : Error()
    }

    override fun execute(parameters: Unit): Flow<Result<Success, Error>> {
        return favouriteBooksRepository.observeFavouriteBooks()
            .map { favouriteBooks ->
                if (favouriteBooks.isNotEmpty()) {
                    Result.Success(Success.FavouriteBooks(favouriteBooks))
                } else {
                    Result.BusinessRuleError(Error.NoFavouritesBooks)
                }
            }
    }
}
