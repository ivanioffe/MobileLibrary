package com.ioffeivan.feature.search.domain.usecase

import com.ioffeivan.core.common.coroutine.IODispatcher
import com.ioffeivan.core.domain.base.FlowUseCase
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private typealias Limit = Int

private const val DEFAULT_LIMIT = 30

internal class ObserveRecentSearchesUseCase(
    private val recentSearchRepository: RecentSearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Limit, ObserveRecentSearchesUseCase.GetRecentSearchesSuccess, Nothing>(dispatcher) {
    sealed class GetRecentSearchesSuccess {
        data class RecentSearches(val items: List<String>) : GetRecentSearchesSuccess()
    }

    operator fun invoke(): Flow<Result<GetRecentSearchesSuccess, Nothing>> =
        invoke(DEFAULT_LIMIT)

    override fun execute(parameters: Limit): Flow<Result<GetRecentSearchesSuccess, Nothing>> {
        return recentSearchRepository.observeRecentSearches(parameters)
            .map { recentSearches ->
                Result.Success(GetRecentSearchesSuccess.RecentSearches(recentSearches))
            }
    }
}
