package com.ioffeivan.feature.search.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.domain.base.UseCase
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import com.ioffeivan.feature.search.domain.usecase.utils.Query
import kotlinx.coroutines.CoroutineDispatcher

internal class DeleteRecentSearchUseCase(
    private val recentSearchRepository: RecentSearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Query, Unit, Nothing>(dispatcher) {
    override suspend fun execute(parameters: Query): Result<Unit, Nothing> {
        recentSearchRepository.deleteRecentSearch(parameters)

        return Result.Success(Unit)
    }
}
