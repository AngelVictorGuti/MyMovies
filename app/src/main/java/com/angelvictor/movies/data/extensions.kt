package com.angelvictor.movies.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.angelvictor.movies.domain.Error
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}

suspend fun <T> safeCall(action: suspend () -> T): Error? = try {
    action()
    null
} catch (e: Exception) {
    e.toError()
}

suspend fun <T> safeCallWithError(action: suspend () -> T): Either<Error, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}