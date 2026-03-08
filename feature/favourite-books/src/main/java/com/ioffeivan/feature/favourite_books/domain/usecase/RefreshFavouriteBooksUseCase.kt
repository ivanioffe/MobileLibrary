package com.ioffeivan.feature.favourite_books.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class RefreshFavouriteBooksUseCase @Inject constructor(
    private val favouriteBooksRepository: FavouriteBooksRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Unit, Unit, Nothing>(dispatcher) {
    override suspend fun execute(parameters: Unit): Result<Unit, Nothing> {
        val result = favouriteBooksRepository.refreshFavouriteBooks()

        return when (result) {
            is DataResult.Success -> {
                Result.Success(Unit)
            }

            is DataResult.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
