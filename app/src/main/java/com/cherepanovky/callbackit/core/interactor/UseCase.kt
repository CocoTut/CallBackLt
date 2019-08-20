package com.cherepanovky.callbackit.core.interactor


import com.cherepanovky.callbackit.core.exception.ErrorHandler
import com.cherepanovky.callbackit.core.exception.Failure
import com.cherepanovky.callbackit.core.functional.Either

abstract class UseCase<out Type : Any, in Params>(private val errorHandler: ErrorHandler) {

    abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        try {
            val result =  run(params)
            onResult(Either.Right(result))
        } catch (e: Exception){
            onResult(errorHandler.handleExceptionToFailure(e))
        }

    }

    class None

}
