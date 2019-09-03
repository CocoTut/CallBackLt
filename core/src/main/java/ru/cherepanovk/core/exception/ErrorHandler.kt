package ru.cherepanovk.core.exception


import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import ru.cherepanovk.core.functional.Either
import ru.cherepanovk.core.platform.NetworkHandler
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ErrorHandler @Inject constructor(private val networkHandler: NetworkHandler) {

    fun handleExceptionToFailure(exception: Throwable): Either.Left<Failure> {
        return if (!networkHandler.isConnected)
            Either.Left(Failure.NetworkConnection)
        else
            when (exception) {
                is HttpException -> Either.Left(getHttpFailure(exception))
                is ConnectException -> Either.Left(Failure.NetworkConnection)
                is SocketTimeoutException -> Either.Left(Failure.TimeOut)
                else -> Either.Left(Failure.ServerError)
            }
    }

    private fun getHttpFailure(exception: HttpException) =
        when (exception.code()) {
            Failure.RESULT_CODE_UNREGISERED_USER -> Failure.Unregistered
            Failure.RESULT_CODE_BAD_API_VERSION -> Failure.BadApiVersion
            Failure.RESULT_CODE_UNAUTHOTIZED -> Failure.Unauthorized
            Failure.RESULT_CODE_BAD_REQUEST -> getBadRequestFailure(exception)
            else -> Failure.ServerError
        }

    fun getBadRequestFailure(error: HttpException): Failure {
        val message = getErrorText(error)
        val failure = Failure.BadRequest
        failure.error = message
        return failure
    }

    fun getErrorText(error: HttpException): String? {
        return error.response()?.errorBody()?.let { errorBody ->
            val errorString = errorBody.string()

            val errorMap: Map<String, List<String>> = try {
                val typeClassMap: Type = object : TypeToken<Map<String, List<String>>>() {}.type
                Gson().fromJson<Map<String, List<String>>>(errorString, typeClassMap)
            } catch (ex: JsonSyntaxException) {
                hashMapOf()
            }

            val errorList: List<String> = try {
                val typeClassList = object : TypeToken<List<String>>() {}.type
                Gson().fromJson<List<String>>(errorString, typeClassList)
            } catch (ex: JsonSyntaxException) {
                listOf()
            }

            when {
                errorMap.isNotEmpty() -> errorMap.toList().first().second.first()
                errorList.isNotEmpty() -> errorList.first()
                else -> null
            }
        }

    }
}