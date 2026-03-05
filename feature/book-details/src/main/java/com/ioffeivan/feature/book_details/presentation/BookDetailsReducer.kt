package com.ioffeivan.feature.book_details.presentation

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.book_details.domain.model.BookDetails

internal class BookDetailsReducer :
    Reducer<BookDetailsState, BookDetailsEvent, BookDetailsEffect> {
    override fun reduce(
        previousState: BookDetailsState,
        event: BookDetailsEvent,
    ): ReducerResult<BookDetailsState, BookDetailsEffect> {
        return when (event) {
            BookDetailsEvent.BackClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = BookDetailsEffect.NavigateToBack,
                )
            }

            BookDetailsEvent.RetryLoadClicked -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                            errorMessage = null,
                        ),
                )
            }

            is BookDetailsEvent.BookDetailsSuccessLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            bookDetails = event.bookDetails,
                            isLoading = false,
                        ),
                )
            }

            is BookDetailsEvent.BookDetailsErrorLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            errorMessage = event.errorMessage,
                            isLoading = false,
                        ),
                )
            }

            is BookDetailsEvent.FavouriteClicked -> {
                ReducerResult(
                    state = previousState.copy(isToggleFavouriteLoading = true),
                )
            }

            BookDetailsEvent.FavouriteToggleSuccess -> {
                val updatedBook =
                    previousState.bookDetails.let {
                        it.copy(isFavourite = !it.isFavourite)
                    }

                ReducerResult(
                    state =
                        previousState.copy(
                            bookDetails = updatedBook,
                            isToggleFavouriteLoading = false,
                        ),
                )
            }

            is BookDetailsEvent.FavouriteToggleError -> {
                ReducerResult(
                    state = previousState.copy(isToggleFavouriteLoading = false),
                    effect = BookDetailsEffect.ShowError(event.errorMessage),
                )
            }
        }
    }
}

internal data class BookDetailsState(
    val bookDetails: BookDetails,
    val isLoading: Boolean,
    val errorMessage: UiText?,
    val isToggleFavouriteLoading: Boolean,
) : Reducer.UiState {
    companion object {
        fun initial(): BookDetailsState {
            return BookDetailsState(
                bookDetails = BookDetails.initial(),
                isLoading = true,
                errorMessage = null,
                isToggleFavouriteLoading = false,
            )
        }
    }
}

internal sealed interface BookDetailsEvent : Reducer.UiEvent {
    data object BackClicked : BookDetailsEvent

    data object RetryLoadClicked : BookDetailsEvent

    data class FavouriteClicked(
        val bookId: String,
        val isFavourite: Boolean,
    ) : BookDetailsEvent

    data class BookDetailsSuccessLoaded(val bookDetails: BookDetails) : BookDetailsEvent

    data class BookDetailsErrorLoaded(val errorMessage: UiText) : BookDetailsEvent

    data object FavouriteToggleSuccess : BookDetailsEvent

    data class FavouriteToggleError(val errorMessage: UiText) : BookDetailsEvent
}

internal sealed interface BookDetailsEffect : Reducer.UiEffect {
    data object NavigateToBack : BookDetailsEffect

    data class ShowError(val errorMessage: UiText) : BookDetailsEffect
}
