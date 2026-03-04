package com.ioffeivan.core.domain.base

import com.ioffeivan.core.common.error.mapToCommonError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Base class for all UseCases in the Domain layer.
 * Encapsulates a specific unit of business logic and ensures it runs on the correct [dispatcher].
 *
 * @param Parameters Input data for the UseCase.
 * @param Success Expected output data on success.
 * @param BusinessRuleError Expected domain-specific error.
 */
abstract class UseCase<in Parameters, Success, BusinessRuleError>(
    private val dispatcher: CoroutineDispatcher,
) {
    /**
     * Executes the use case logic on the designated [dispatcher].
     * @return A [Result] containing either data, a business error, or a system error.
     */
    suspend operator fun invoke(parameters: Parameters): Result<Success, BusinessRuleError> {
        return try {
            withContext(dispatcher) {
                execute(parameters)
            }
        } catch (e: Exception) {
            Result.Error(e.mapToCommonError())
        }
    }

    /**
     * Implementation of the specific business logic.
     */
    protected abstract suspend fun execute(parameters: Parameters): Result<Success, BusinessRuleError>
}
