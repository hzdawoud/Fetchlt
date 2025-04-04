package com.hzdawoud.fetchlt.utils.network

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Either<out L, out R> {
    data class Success<out R>(val data: R) : Either<Nothing, R>()
    data class Error<out L>(val error: L) : Either<L, Nothing>()
}

sealed class ErrorEntity {
    data object HttpError : ErrorEntity()
    data object ConnectionError : ErrorEntity()
    data object UnexpectedError : ErrorEntity()
    data object EmptyResponse : ErrorEntity()
    data object NotFound : ErrorEntity()
}

object NetworkHandler {
    fun <T, R> Response<T>.toEither(tag: String, transform: (T) -> R): Either<ErrorEntity, R> =
        try {
            when {
                isSuccessful -> body()?.let {
                    Log.d(tag, "Network call - success")
                    Either.Success(transform(it))
                } ?: Either.Error(ErrorEntity.EmptyResponse)

                else -> Either.Error(ErrorEntity.HttpError)
            }
        } catch (e: Exception) {
            Either.Error(e.toErrorEntity())
        }

    private fun Throwable.toErrorEntity(): ErrorEntity = when (this) {
        is IOException -> ErrorEntity.ConnectionError
        is HttpException -> ErrorEntity.HttpError
        else -> ErrorEntity.UnexpectedError
    }
}
