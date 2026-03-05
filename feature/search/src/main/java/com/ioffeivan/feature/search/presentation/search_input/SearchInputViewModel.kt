package com.ioffeivan.feature.search.presentation.search_input

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.domain.base.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.search.domain.usecase.ClearRecentSearchesUseCase
import com.ioffeivan.feature.search.domain.usecase.DeleteRecentSearchUseCase
import com.ioffeivan.feature.search.domain.usecase.ObserveRecentSearchesUseCase
import com.ioffeivan.feature.search.domain.usecase.SaveRecentSearchUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchInputViewModel @Inject constructor(
    private val observeRecentSearchesUseCase: ObserveRecentSearchesUseCase,
    private val saveRecentSearchUseCase: SaveRecentSearchUseCase,
    private val deleteRecentSearchUseCase: Lazy<DeleteRecentSearchUseCase>,
    private val clearRecentSearchesUseCase: Lazy<ClearRecentSearchesUseCase>,
) : BaseViewModel<SearchInputState, SearchInputEvent, SearchInputEffect>(
        initialState = SearchInputState.initial(),
        reducer = SearchInputReducer(),
    ) {
    override fun onEvent(event: SearchInputEvent) {
        sendEvent(event)

        when (event) {
            is SearchInputEvent.SearchClicked -> {
                onSearchClick(event.query)
            }

            is SearchInputEvent.RecentSearchDeleted -> {
                onRecentSearchDeleted(event.query)
            }

            SearchInputEvent.RecentSearchesCleared -> {
                onRecentSearchesCleared()
            }

            else -> {}
        }
    }

    override suspend fun initialDataLoad() {
        observeRecentSearchesUseCase()
            .onEach { result ->
                result.onSuccess { recentSearches ->
                    when (recentSearches) {
                        is ObserveRecentSearchesUseCase.Success.RecentSearches -> {
                            sendEvent(
                                SearchInputEvent.RecentSearchesReceived(recentSearches.items),
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onSearchClick(query: String) {
        viewModelScope.launch {
            saveRecentSearchUseCase(query)
        }
    }

    private fun onRecentSearchDeleted(query: String) {
        viewModelScope.launch {
            deleteRecentSearchUseCase.get().invoke(query)
        }
    }

    private fun onRecentSearchesCleared() {
        viewModelScope.launch {
            clearRecentSearchesUseCase.get().invoke(Unit)
        }
    }
}
