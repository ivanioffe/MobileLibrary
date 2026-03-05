package com.ioffeivan.feature.book_details.presentation

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.domain.base.onError
import com.ioffeivan.core.domain.base.onSuccess
import com.ioffeivan.core.domain.usecase.AddBookToFavouritesUseCase
import com.ioffeivan.core.domain.usecase.RemoveBookFromFavouritesUseCase
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.core.presentation.asStringRes
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.book_details.domain.usecase.GetBookDetailsUseCase
import dagger.Lazy
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = BookDetailsViewModel.Factory::class)
internal class BookDetailsViewModel @AssistedInject constructor(
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val addBookToFavouritesUseCase: Lazy<AddBookToFavouritesUseCase>,
    private val removeBookFromFavouritesUseCase: Lazy<RemoveBookFromFavouritesUseCase>,
    @Assisted private val bookId: String,
) : BaseViewModel<BookDetailsState, BookDetailsEvent, BookDetailsEffect>(
        initialState = BookDetailsState.initial(),
        reducer = BookDetailsReducer(),
    ) {
    override fun onEvent(event: BookDetailsEvent) {
        sendEvent(event)

        when (event) {
            is BookDetailsEvent.RetryLoadClicked -> {
                loadBookDetails()
            }

            is BookDetailsEvent.FavouriteClicked -> {
                onFavouriteClicked(
                    bookId = event.bookId,
                    isFavourite = event.isFavourite,
                )
            }

            else -> {}
        }
    }

    override suspend fun initialDataLoad() {
        loadBookDetails()
    }

    private fun loadBookDetails() {
        viewModelScope.launch {
            getBookDetailsUseCase(bookId)
                .onSuccess {
                    when (it) {
                        is GetBookDetailsUseCase.Success.BookDetailsData -> {
                            sendEvent(BookDetailsEvent.BookDetailsSuccessLoaded(it.bookDetails))
                        }
                    }
                }
                .onError {
                    sendEvent(
                        BookDetailsEvent.BookDetailsErrorLoaded(
                            UiText.StringResource(it.asStringRes()),
                        ),
                    )
                }
        }
    }

    private fun onFavouriteClicked(bookId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                removeBookFromFavouritesUseCase.get().invoke(bookId)
                    .onSuccess {
                        sendEvent(BookDetailsEvent.FavouriteToggleSuccess)
                    }
                    .onError { error ->
                        sendEvent(
                            BookDetailsEvent.FavouriteToggleError(
                                UiText.StringResource(error.asStringRes()),
                            ),
                        )
                    }
            } else {
                addBookToFavouritesUseCase.get().invoke(bookId)
                    .onSuccess {
                        sendEvent(BookDetailsEvent.FavouriteToggleSuccess)
                    }
                    .onError { error ->
                        sendEvent(
                            BookDetailsEvent.FavouriteToggleError(
                                UiText.StringResource(error.asStringRes()),
                            ),
                        )
                    }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            bookId: String,
        ): BookDetailsViewModel
    }
}
