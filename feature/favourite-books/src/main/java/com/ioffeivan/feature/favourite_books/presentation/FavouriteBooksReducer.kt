package com.ioffeivan.feature.favourite_books.presentation

import com.ioffeivan.core.model.Books
import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText

internal class FavouriteBooksReducer :
    Reducer<FavouriteBooksState, FavouriteBooksEvent, FavouriteBooksEffect> {
    override fun reduce(
        previousState: FavouriteBooksState,
        event: FavouriteBooksEvent,
    ): ReducerResult<FavouriteBooksState, FavouriteBooksEffect> {
        return when (event) {
            is FavouriteBooksEvent.BookClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = FavouriteBooksEffect.NavigateToBookDetails(event.bookId),
                )
            }

            FavouriteBooksEvent.BackClicked -> {
                ReducerResult(
                    state = previousState,
                    effect = FavouriteBooksEffect.NavigateToBack,
                )
            }

            is FavouriteBooksEvent.FavouriteClicked -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            bookForRemoveId = event.bookId,
                        ),
                )
            }

            is FavouriteBooksEvent.RemoveBookConfirmed -> {
                ReducerResult(
                    state = previousState.copy(isBookRemoving = true),
                )
            }

            FavouriteBooksEvent.RemoveBookDismissed -> {
                ReducerResult(
                    state = previousState.copy(bookForRemoveId = null),
                )
            }

            FavouriteBooksEvent.RetryLoadClicked -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                            errorMessage = null,
                        ),
                )
            }

            is FavouriteBooksEvent.FavouriteBooksSuccessLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            favouriteBooks = event.favouriteBooks,
                            isLoading = false,
                        ),
                )
            }

            is FavouriteBooksEvent.FavouriteBooksErrorLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            errorMessage = event.errorMessage,
                            isLoading = false,
                        ),
                )
            }

            FavouriteBooksEvent.NoFavouriteBooks -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            favouriteBooks = Books(listOf()),
                            isLoading = false,
                        ),
                )
            }

            FavouriteBooksEvent.BookSuccessRemoved -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            favouriteBooks =
                                previousState.favouriteBooks.copy(
                                    previousState.favouriteBooks.items.filter {
                                        it.id != previousState.bookForRemoveId
                                    },
                                ),
                            bookForRemoveId = null,
                            isBookRemoving = false,
                        ),
                )
            }

            is FavouriteBooksEvent.BookErrorRemoved -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            bookForRemoveId = null,
                            isBookRemoving = false,
                        ),
                    effect = FavouriteBooksEffect.ShowError(event.errorMessage),
                )
            }
        }
    }
}

internal data class FavouriteBooksState(
    val favouriteBooks: Books,
    val isLoading: Boolean,
    val errorMessage: UiText?,
    val bookForRemoveId: String?,
    val isBookRemoving: Boolean,
) : Reducer.UiState {
    companion object {
        fun initial(): FavouriteBooksState {
            return FavouriteBooksState(
                favouriteBooks = Books(listOf()),
                isLoading = true,
                errorMessage = null,
                bookForRemoveId = null,
                isBookRemoving = false,
            )
        }
    }
}

internal sealed interface FavouriteBooksEvent : Reducer.UiEvent {
    data object BackClicked : FavouriteBooksEvent

    data class BookClicked(val bookId: String) : FavouriteBooksEvent

    data class FavouriteClicked(val bookId: String) : FavouriteBooksEvent

    data class RemoveBookConfirmed(val bookId: String) : FavouriteBooksEvent

    data object RemoveBookDismissed : FavouriteBooksEvent

    data object RetryLoadClicked : FavouriteBooksEvent

    data class FavouriteBooksSuccessLoaded(val favouriteBooks: Books) : FavouriteBooksEvent

    data object NoFavouriteBooks : FavouriteBooksEvent

    data class FavouriteBooksErrorLoaded(val errorMessage: UiText) : FavouriteBooksEvent

    data object BookSuccessRemoved : FavouriteBooksEvent

    data class BookErrorRemoved(val errorMessage: UiText) : FavouriteBooksEvent
}

internal sealed interface FavouriteBooksEffect : Reducer.UiEffect {
    data object NavigateToBack : FavouriteBooksEffect

    data class NavigateToBookDetails(val id: String) : FavouriteBooksEffect

    data class ShowError(val errorMessage: UiText) : FavouriteBooksEffect
}
