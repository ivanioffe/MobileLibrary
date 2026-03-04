package com.ioffeivan.feature.search.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.domain.base.FlowUseCase
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private typealias Limit = Int

private const val DEFAULT_LIMIT = 30

internal class ObserveRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Limit, ObserveRecentSearchesUseCase.Success, Nothing>(dispatcher) {
    sealed class Success {
        data class RecentSearches(val items: List<String>) : Success()
    }

    operator fun invoke(): Flow<Result<Success, Nothing>> =
        invoke(DEFAULT_LIMIT)

    override fun execute(parameters: Limit): Flow<Result<Success, Nothing>> {
        return recentSearchRepository.observeRecentSearches(parameters)
            .map { recentSearches ->
                Result.Success(Success.RecentSearches(recentSearches))
            }
    }
}
