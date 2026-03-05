package com.ioffeivan.core.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import com.ioffeivan.core.domain.usecase.utils.BookId
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoveBookFromFavouritesUseCase @Inject constructor(
    private val favouriteBooksRepository: FavouriteBooksRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<BookId, Unit, Nothing>(dispatcher) {
    override suspend fun execute(parameters: BookId): Result<Unit, Nothing> {
        val result = favouriteBooksRepository.removeBookFromFavourites(parameters)

        return when (result) {
            is DataResult.Success<*> -> {
                Result.Success(Unit)
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
