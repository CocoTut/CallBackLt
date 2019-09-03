package ru.cherepanovk.core.interactor


import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.functional.Either

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
