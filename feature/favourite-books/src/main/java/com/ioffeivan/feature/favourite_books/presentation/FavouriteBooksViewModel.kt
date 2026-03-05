package com.ioffeivan.feature.favourite_books.presentation

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.domain.base.onBusinessRuleError
import com.ioffeivan.core.domain.base.onError
import com.ioffeivan.core.domain.base.onSuccess
import com.ioffeivan.core.domain.usecase.RemoveBookFromFavouritesUseCase
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.core.presentation.asStringRes
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.favourite_books.domain.usecase.GetFavouriteBooksUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouriteBooksViewModel @Inject constructor(
    private val getFavouriteBooksUseCase: GetFavouriteBooksUseCase,
    private val removeBookFromFavouritesUseCase: Lazy<RemoveBookFromFavouritesUseCase>,
) : BaseViewModel<FavouriteBooksState, FavouriteBooksEvent, FavouriteBooksEffect>(
        initialState = FavouriteBooksState.initial(),
        reducer = FavouriteBooksReducer(),
    ) {
    override fun onEvent(event: FavouriteBooksEvent) {
        sendEvent(event)

        when (event) {
            is FavouriteBooksEvent.RetryLoadClicked -> {
                onRetryLoadClicked()
            }

            is FavouriteBooksEvent.RemoveBookConfirmed -> {
                onRemoveConfirmed(event.bookId)
            }

            else -> {}
        }
    }

    override suspend fun initialDataLoad() {
        getFavouriteBooks()
    }

    fun onRetryLoadClicked() {
        viewModelScope.launch {
            getFavouriteBooks()
        }
    }

    private suspend fun getFavouriteBooks() {
        getFavouriteBooksUseCase(Unit)
            .onSuccess {
                when (it) {
                    is GetFavouriteBooksUseCase.Success.FavouriteBooks -> {
                        sendEvent(FavouriteBooksEvent.FavouriteBooksSuccessLoaded(it.books))
                    }
                }
            }
            .onBusinessRuleError {
                when (it) {
                    GetFavouriteBooksUseCase.Error.NoFavouritesBooks -> {
                        sendEvent(FavouriteBooksEvent.NoFavouriteBooks)
                    }
                }
            }
            .onError {
                sendEvent(
                    FavouriteBooksEvent.FavouriteBooksErrorLoaded(
                        UiText.StringResource(it.asStringRes()),
                    ),
                )
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
}
