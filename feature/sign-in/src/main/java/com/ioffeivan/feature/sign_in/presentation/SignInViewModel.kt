package com.ioffeivan.feature.sign_in.presentation

import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.sign_in.R
import com.ioffeivan.feature.sign_in.data.source.remote.BooksApiManager
import com.ioffeivan.feature.sign_in.domain.model.AuthData
import com.ioffeivan.feature.sign_in.domain.usecase.SaveAuthDataUseCase
import com.ioffeivan.feature.sign_in.domain.usecase.SaveUserUseCase
import com.ioffeivan.feature.sign_in.presentation.mapper.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val booksApiManager: BooksApiManager,
    private val saveUserUseCase: SaveUserUseCase,
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
) : ViewModel() {
    private val _effect = Channel<SignInEffect>(capacity = Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleSignIn(googleIdTokenCredential: GoogleIdTokenCredential?) {
        if (googleIdTokenCredential != null) {
            viewModelScope.launch {
                saveUserUseCase(googleIdTokenCredential.toUser())
                requestAccessToBooks()
            }
        } else {
            showError(UiText.StringResource(R.string.sign_in_error_general))
        }
    }

    fun saveAccessToken(accessToken: String?) {
        accessToken?.let {
            viewModelScope.launch {
                saveAuthDataUseCase(AuthData(accessToken))
            }
        } ?: showError(UiText.StringResource(R.string.sign_in_error_books_access))
    }

    fun showError(message: UiText) {
        _effect.trySend(SignInEffect.ShowError(message))
    }

    private suspend fun requestAccessToBooks() {
        booksApiManager.requestAccessToBooks()?.let { authResult ->
            if (authResult.hasResolution()) {
                authResult.pendingIntent?.let { pendingIntent ->
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(pendingIntent.intentSender)
                            .build()

                    _effect.trySend(SignInEffect.ShowResolution(intentSenderRequest))
                }
            } else {
                saveAccessToken(authResult.accessToken)
            }
        } ?: showError(UiText.StringResource(R.string.sign_in_error_books_access))
    }
}

sealed interface SignInEffect {
    data class ShowResolution(val intentSenderRequest: IntentSenderRequest) : SignInEffect

    data class ShowError(val message: UiText) : SignInEffect
}
