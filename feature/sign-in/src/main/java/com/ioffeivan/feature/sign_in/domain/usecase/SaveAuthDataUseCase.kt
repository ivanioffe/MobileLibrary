package com.ioffeivan.feature.sign_in.domain.usecase

import com.ioffeivan.feature.sign_in.domain.model.AuthData
import com.ioffeivan.feature.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class SaveAuthDataUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {
    suspend operator fun invoke(authData: AuthData) {
        signInRepository.saveAuthData(authData)
    }
}
