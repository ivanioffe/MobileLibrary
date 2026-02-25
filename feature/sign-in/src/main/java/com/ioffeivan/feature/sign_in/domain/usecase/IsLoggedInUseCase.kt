package com.ioffeivan.feature.sign_in.domain.usecase

import com.ioffeivan.feature.sign_in.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {
    operator fun invoke(): Flow<Boolean> = signInRepository.isLoggedIn
}
