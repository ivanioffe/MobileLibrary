package com.ioffeivan.feature.search.presentation.search_results

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.domain.base.onBusinessRuleError
import com.ioffeivan.core.domain.base.onError
import com.ioffeivan.core.domain.base.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.core.presentation.asStringRes
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.search.domain.model.SearchParams
import com.ioffeivan.feature.search.domain.usecase.SearchUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SearchResultsViewModel.Factory::class)
internal class SearchResultsViewModel @AssistedInject constructor(
    private val searchUseCase: SearchUseCase,
    @Assisted private val query: String,
) : BaseViewModel<SearchResultsState, SearchResultsEvent, SearchResultsEffect>(
        initialState = SearchResultsState.initial(query),
        reducer = SearchResultsReducer(),
    ) {
    override fun onEvent(event: SearchResultsEvent) {
        sendEvent(event)
    }

    @SuppressLint("ResourceType")
    override suspend fun initialDataLoad() {
        viewModelScope.launch {
            searchUseCase(SearchParams(query))
                .onSuccess {
                    when (it) {
                        is SearchUseCase.Success.BooksData -> {
                            sendEvent(SearchResultsEvent.BooksSuccessLoaded(it.books))
                        }
                    }
                }
                .onBusinessRuleError {
                    sendEvent(SearchResultsEvent.NoBooksFound)
                }
                .onError {
                    sendEvent(
                        SearchResultsEvent.BooksErrorLoaded(
                            UiText.StringResource(it.asStringRes()),
                        ),
                    )
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            query: String,
        ): SearchResultsViewModel
    }
}
