package com.beksar.kmm.architecture.domain

import com.beksar.kmm.architecture.core.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Базовый UseCase для бизнес логики
 */
abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract suspend fun execute(params: Params, scope: CoroutineScope): Either<Throwable, Type>

    open suspend operator fun invoke(
        scope: CoroutineScope,
        params: Params,
    ): Either<Throwable, Type> {
        val deferred = scope.async { execute(params, this) }
        return withContext(scope.coroutineContext) {
            try {
                deferred.await()
            } catch (e: Exception) {
                Either.Left(e)
            }
        }
    }
}