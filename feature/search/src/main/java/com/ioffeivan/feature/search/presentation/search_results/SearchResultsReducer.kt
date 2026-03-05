package com.ioffeivan.feature.search.presentation.search_results

import com.ioffeivan.core.model.Books
import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText

internal class SearchResultsReducer :
    Reducer<SearchResultsState, SearchResultsEvent, SearchResultsEffect> {
    override fun reduce(
        previousState: SearchResultsState,
        event: SearchResultsEvent,
    ): ReducerResult<SearchResultsState, SearchResultsEffect> {
        return when (event) {
            is SearchResultsEvent.BookClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = SearchResultsEffect.NavigateToBookDetails(event.id),
                )
            }

            SearchResultsEvent.RetryLoadClicked -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                            errorMessage = null,
                        ),
                )
            }

            SearchResultsEvent.BackClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = SearchResultsEffect.NavigateToBack,
                )
            }

            is SearchResultsEvent.BooksSuccessLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            books = event.books,
                            isLoading = false,
                        ),
                )
            }

            is SearchResultsEvent.BooksErrorLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            errorMessage = event.errorMessage,
                            isLoading = false,
                        ),
                )
            }

            SearchResultsEvent.NoBooksFound -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            books = Books(listOf()),
                            isLoading = false,
                        ),
                )
            }
        }
    }
}

internal data class SearchResultsState(
    val query: String,
    val books: Books,
    val isLoading: Boolean,
    val errorMessage: UiText?,
) : Reducer.UiState {
    companion object {
        fun initial(query: String): SearchResultsState {
            return SearchResultsState(
                query = query,
                books = Books(listOf()),
                isLoading = true,
                errorMessage = null,
            )
        }
    }
}

internal sealed interface SearchResultsEvent : Reducer.UiEvent {
    data object BackClicked : SearchResultsEvent

    data class BookClicked(val id: String) : SearchResultsEvent

    data object RetryLoadClicked : SearchResultsEvent

    data class BooksSuccessLoaded(val books: Books) : SearchResultsEvent

    data object NoBooksFound : SearchResultsEvent

    data class BooksErrorLoaded(val errorMessage: UiText) : SearchResultsEvent
}

internal sealed interface SearchResultsEffect : Reducer.UiEffect {
    data object NavigateToBack : SearchResultsEffect

    data class NavigateToBookDetails(val id: String) : SearchResultsEffect
}
