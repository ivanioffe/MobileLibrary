package com.ioffeivan.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.domain.base.onBusinessRuleError
import com.ioffeivan.core.domain.base.onError
import com.ioffeivan.core.domain.base.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.core.presentation.asStringRes
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.home.domain.usecase.GetRecommendedBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRecommendedBooksUseCase: GetRecommendedBooksUseCase,
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(
        initialState = HomeState.initial(),
        reducer = HomeReducer(),
    ) {
    override fun onEvent(event: HomeEvent) {
        sendEvent(event)

        when (event) {
            is HomeEvent.RetryLoadClicked -> {
                onRetryLoadClicked()
            }

            else -> {}
        }
    }

    override suspend fun initialDataLoad() {
        getRecommendedBooks()
    }

    private fun onRetryLoadClicked() {
        viewModelScope.launch {
            getRecommendedBooks()
        }
    }

    private suspend fun getRecommendedBooks() {
        getRecommendedBooksUseCase(Unit)
            .onSuccess {
                when (it) {
                    is GetRecommendedBooksUseCase.Success.RecommendedBooks -> {
                        sendEvent(HomeEvent.RecommendedBooksSuccessLoaded(it.books))
                    }
                }
            }
            .onBusinessRuleError {
                when (it) {
                    GetRecommendedBooksUseCase.Error.NoRecommendedBooks -> {
                        sendEvent(HomeEvent.NoRecommendedBooks)
                    }
                }
            }
            .onError {
                sendEvent(
                    HomeEvent.RecommendedBooksErrorLoaded(
                        UiText.StringResource(it.asStringRes()),
                    ),
                )
            }
    }
}
