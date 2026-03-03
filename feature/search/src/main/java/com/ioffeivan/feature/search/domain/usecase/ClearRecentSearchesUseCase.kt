package com.ioffeivan.feature.search.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class ClearRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Unit, Unit, Nothing>(dispatcher) {
    override suspend fun execute(parameters: Unit): Result<Unit, Nothing> {
        recentSearchRepository.clearRecentSearches()

        return Result.Success(Unit)
    }
}
