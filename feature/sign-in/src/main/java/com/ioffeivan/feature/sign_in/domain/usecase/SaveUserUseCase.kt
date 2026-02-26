package com.ioffeivan.feature.sign_in.domain.usecase

import com.ioffeivan.core.model.User
import com.ioffeivan.feature.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {
    suspend operator fun invoke(user: User) {
        signInRepository.saveUser(user)
    }
}
