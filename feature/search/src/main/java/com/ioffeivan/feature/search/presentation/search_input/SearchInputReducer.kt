package com.ioffeivan.feature.search.presentation.search_input

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult

internal class SearchInputReducer : Reducer<SearchInputState, SearchInputEvent, SearchInputEffect> {
    override fun reduce(
        previousState: SearchInputState,
        event: SearchInputEvent,
    ): ReducerResult<SearchInputState, SearchInputEffect> {
        return when (event) {
            is SearchInputEvent.QueryChanged -> {
                ReducerResult(
                    state = previousState.copy(query = event.query),
                )
            }

            is SearchInputEvent.SearchClick -> {
                ReducerResult(
                    state = previousState,
                    effect = SearchInputEffect.NavigateToSearchResults(event.query),
                )
            }

            is SearchInputEvent.RecentSearchClicked -> {
                ReducerResult(
                    state = previousState.copy(query = event.query),
                    effect = SearchInputEffect.NavigateToSearchResults(event.query),
                )
            }

            is SearchInputEvent.RecentSearchDeleted, SearchInputEvent.RecentSearchesCleared -> {
                ReducerResult(
                    state = previousState,
                )
            }

            SearchInputEvent.BackClick -> {
                ReducerResult(
                    state = previousState,
                    effect = SearchInputEffect.NavigateToBack,
                )
            }

            is SearchInputEvent.RecentSearchesReceived -> {
                ReducerResult(
                    state = previousState.copy(recentSearches = event.recentSearches),
                )
            }
        }
    }
}

internal data class SearchInputState(
    val query: String,
    val recentSearches: List<String>,
) : Reducer.UiState {
    companion object {
        fun initial(): SearchInputState {
            return SearchInputState(
                query = "",
                recentSearches = listOf(),
            )
        }
    }
}

internal sealed interface SearchInputEvent : Reducer.UiEvent {
    data class QueryChanged(val query: String) : SearchInputEvent

    data class SearchClick(val query: String) : SearchInputEvent

    data class RecentSearchClicked(val query: String) : SearchInputEvent

    data class RecentSearchDeleted(val query: String) : SearchInputEvent

    data object RecentSearchesCleared : SearchInputEvent

    data object BackClick : SearchInputEvent

    data class RecentSearchesReceived(val recentSearches: List<String>) : SearchInputEvent
}

internal sealed interface SearchInputEffect : Reducer.UiEffect {
    data class NavigateToSearchResults(val query: String) : SearchInputEffect

    data object NavigateToBack : SearchInputEffect
}
