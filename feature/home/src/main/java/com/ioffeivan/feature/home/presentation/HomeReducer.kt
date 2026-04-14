package com.ioffeivan.feature.home.presentation

import com.ioffeivan.core.model.Books
import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText

internal class HomeReducer :
    Reducer<HomeState, HomeEvent, HomeEffect> {
    override fun reduce(
        previousState: HomeState,
        event: HomeEvent,
    ): ReducerResult<HomeState, HomeEffect> {
        return when (event) {
            HomeEvent.FavouriteBooksClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = HomeEffect.NavigateToFavouriteBooks,
                )
            }

            HomeEvent.SearchClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = HomeEffect.NavigateToSearch,
                )
            }

            is HomeEvent.RecommendedBookClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = HomeEffect.NavigateToBookDetails(event.id),
                )
            }

            is HomeEvent.RecommendedBooksSuccessLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            recommendedBooks = event.books,
                            isLoading = false,
                        ),
                )
            }

            is HomeEvent.RecommendedBooksErrorLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            errorMessage = event.errorMessage,
                            isLoading = false,
                        ),
                )
            }

            HomeEvent.RetryLoadClicked -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                            errorMessage = null,
                        ),
                )
            }

            HomeEvent.NoRecommendedBooks -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            recommendedBooks = Books(listOf()),
                            isLoading = false,
                        ),
                )
            }
        }
    }
}

internal data class HomeState(
    val recommendedBooks: Books,
    val isLoading: Boolean,
    val errorMessage: UiText?,
) : Reducer.UiState {
    companion object {
        fun initial(): HomeState {
            return HomeState(
                recommendedBooks = Books(listOf()),
                isLoading = true,
                errorMessage = null,
            )
        }
    }
}

internal sealed interface HomeEvent : Reducer.UiEvent {
    data object FavouriteBooksClicked : HomeEvent

    data object SearchClicked : HomeEvent

    data class RecommendedBookClicked(val id: String) : HomeEvent

    data object RetryLoadClicked : HomeEvent

    data class RecommendedBooksSuccessLoaded(val books: Books) : HomeEvent

    data object NoRecommendedBooks : HomeEvent

    data class RecommendedBooksErrorLoaded(val errorMessage: UiText) : HomeEvent
}

internal sealed interface HomeEffect : Reducer.UiEffect {
    data object NavigateToFavouriteBooks : HomeEffect

    data object NavigateToSearch : HomeEffect

    data class NavigateToBookDetails(val id: String) : HomeEffect
}
