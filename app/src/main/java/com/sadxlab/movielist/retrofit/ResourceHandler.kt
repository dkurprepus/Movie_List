package com.sadxlab.movielist.retrofit

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketTimeoutException

object ResourceHandler {

    private const val SOCKET_TIMEOUT_CODE = -200

    fun <T : Any> handleSuccess(data: T): Resource<T> = Resource.success(data)

    fun <T: Any?> handleNullableSuccess (data: T?) : Resource<T?> = Resource.nullableSuccess(data)
    fun <T: Any?> handleNullableDeleteSuccess (data: T) : Resource<T> = Resource.nullableSuccessDelete(data)

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getError(e.code(), e.message(), e.response()?.errorBody()?.string()), null)
            is SocketTimeoutException -> Resource.error(getError(SOCKET_TIMEOUT_CODE, "TIMEOUT", null), null)
            else -> Resource.error(getError(Int.MAX_VALUE, e.message.toString(), null), null)
        }
    }

    private fun getError(code: Int, message: String, body: String?): ResponseError {
        var error: ServiceError? = null
        body?.let {
            try {
                error = Gson().fromJson(it, ServiceError::class.java)
            } catch (e: JsonSyntaxException) {
                Log.e("HTTP_ERROR", e.localizedMessage ?: "Error while deserializing JSON item")
            }
        }

        val msg = error?.error ?: message
        return when (code) {
            SOCKET_TIMEOUT_CODE -> ResponseError.TIMEOUT
            401 -> ResponseError.EXPIRED_TOKEN.also { it.errorMessage = msg }
            403 -> ResponseError.FORBIDDEN.also { it.errorMessage = msg }
            404 -> ResponseError.UNAUTHORIZED.also { it.errorMessage = msg }
            else -> ResponseError.UNKNOWN.also { it.errorMessage = msg }
        }
    }
}