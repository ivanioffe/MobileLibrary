package com.ioffeivan.feature.favourite_books.presentation

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.domain.base.onBusinessRuleError
import com.ioffeivan.core.domain.base.onError
import com.ioffeivan.core.domain.base.onSuccess
import com.ioffeivan.core.domain.usecase.RemoveBookFromFavouritesUseCase
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.core.presentation.asStringRes
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.favourite_books.domain.usecase.ObserveFavouriteBooksUseCase
import com.ioffeivan.feature.favourite_books.domain.usecase.RefreshFavouriteBooksUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

// Как держать Snackbar на нескольких экранах
// Переделать на PullToRefresh и убрать isLoading и (удалять напрямую без Dialog)?
@HiltViewModel
internal class FavouriteBooksViewModel @Inject constructor(
    private val observeFavouriteBooksUseCase: ObserveFavouriteBooksUseCase,
    private val refreshFavouriteBooksUseCase: RefreshFavouriteBooksUseCase,
    private val removeBookFromFavouritesUseCase: Lazy<RemoveBookFromFavouritesUseCase>,
) : BaseViewModel<FavouriteBooksState, FavouriteBooksEvent, FavouriteBooksEffect>(
        initialState = FavouriteBooksState.initial(),
        reducer = FavouriteBooksReducer(),
    ) {
    override fun onEvent(event: FavouriteBooksEvent) {
        sendEvent(event)

        when (event) {
            is FavouriteBooksEvent.RefreshClicked -> {
                onRetryLoadClicked()
            }

            is FavouriteBooksEvent.RemoveBookConfirmed -> {
                onRemoveConfirmed(event.bookId)
            }

            else -> {}
        }
    }

    override suspend fun initialDataLoad() {
        observeFavouriteBooks()
        refreshFavouriteBooksUseCase(Unit)
            .onSuccess {
                sendEvent(FavouriteBooksEvent.InitialRefreshSuccess)
            }
            .onError {
                sendEvent(FavouriteBooksEvent.InitialRefreshError)
            }
    }

    private fun onRetryLoadClicked() {
        viewModelScope.launch {
            refreshFavouriteBooksUseCase(Unit)
                .onSuccess {
                    sendEvent(FavouriteBooksEvent.RefreshSuccess)
                }
                .onError {
                    sendEvent(
                        FavouriteBooksEvent.RefreshError(
                            UiText.StringResource(it.asStringRes()),
                        ),
                    )
                }
        }
    }

    private fun onRemoveConfirmed(bookId: String) {
        viewModelScope.launch {
            removeBookFromFavouritesUseCase.get().invoke(bookId)
                .onSuccess {
                    sendEvent(FavouriteBooksEvent.BookSuccessRemoved)
                }
                .onError {
                    sendEvent(
                        FavouriteBooksEvent.BookErrorRemoved(
                            UiText.StringResource(it.asStringRes()),
                        ),
                    )
                }
        }
    }

    private fun observeFavouriteBooks() {
        observeFavouriteBooksUseCase(Unit)
            .onEach { result ->
                result
                    .onSuccess {
                        when (it) {
                            is ObserveFavouriteBooksUseCase.Success.FavouriteBooks -> {
                                sendEvent(FavouriteBooksEvent.FavouriteBooksSuccessLoaded(it.books))
                            }
                        }
                    }
                    .onBusinessRuleError {
                        when (it) {
                            ObserveFavouriteBooksUseCase.Error.NoFavouritesBooks -> {
                                sendEvent(FavouriteBooksEvent.NoFavouriteBooks)
                            }
                        }
                    }
            }
            .launchIn(viewModelScope)
    }
}
