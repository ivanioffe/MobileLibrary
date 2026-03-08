package com.ioffeivan.feature.favourite_books.presentation

import com.ioffeivan.core.model.Book
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

            FavouriteBooksEvent.RefreshClicked -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isRefreshing = true,
                        ),
                )
            }

            is FavouriteBooksEvent.FavouriteBooksSuccessLoaded -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            favouriteBooks = event.books,
                        ),
                )
            }

            FavouriteBooksEvent.NoFavouriteBooks -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            favouriteBooks = listOf(),
                        ),
                )
            }

            FavouriteBooksEvent.InitialRefreshSuccess -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isRefreshing = false,
                        ),
                )
            }

            FavouriteBooksEvent.InitialRefreshError -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isRefreshing = false,
                        ),
                )
            }

            FavouriteBooksEvent.RefreshSuccess -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isRefreshing = false,
                        ),
                )
            }

            is FavouriteBooksEvent.RefreshError -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isRefreshing = false,
                        ),
                    effect = FavouriteBooksEffect.ShowError(event.errorMessage),
                )
            }

            FavouriteBooksEvent.BookSuccessRemoved -> {
                ReducerResult(
                    state =
                        previousState.copy(
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
    val favouriteBooks: List<Book>,
    val isRefreshing: Boolean?,
    val bookForRemoveId: String?,
    val isBookRemoving: Boolean,
) : Reducer.UiState {
    companion object {
        fun initial(): FavouriteBooksState {
            return FavouriteBooksState(
                favouriteBooks = listOf(),
                isRefreshing = null, // null - Initial refresh
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

    data object RefreshClicked : FavouriteBooksEvent

    data class FavouriteBooksSuccessLoaded(val books: List<Book>) : FavouriteBooksEvent

    data object NoFavouriteBooks : FavouriteBooksEvent

    data object InitialRefreshSuccess : FavouriteBooksEvent

    data object InitialRefreshError : FavouriteBooksEvent

    data object RefreshSuccess : FavouriteBooksEvent

    data class RefreshError(val errorMessage: UiText) : FavouriteBooksEvent

    data object BookSuccessRemoved : FavouriteBooksEvent

    data class BookErrorRemoved(val errorMessage: UiText) : FavouriteBooksEvent
}

internal sealed interface FavouriteBooksEffect : Reducer.UiEffect {
    data object NavigateToBack : FavouriteBooksEffect

    data class NavigateToBookDetails(val id: String) : FavouriteBooksEffect

    data class ShowError(val errorMessage: UiText) : FavouriteBooksEffect
}
