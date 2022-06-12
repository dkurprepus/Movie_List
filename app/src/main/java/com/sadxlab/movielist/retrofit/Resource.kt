package com.sadxlab.movielist.retrofit

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Serializable

data class Resource<out T> (val status: Status, val data: T?, val e: ResponseError? = null) {

    companion object {
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data)

        fun <T> nullableSuccess(data: T?) : Resource<T?> = Resource(Status.SUCCESS_NULLABLE, data)
        fun <T> nullableSuccessDelete(data: T) : Resource<T> = Resource(Status.SUCCESS_NULLABLE, data)

        fun <T> error(e: ResponseError, data: T?): Resource<T> = Resource(Status.ERROR, data, e)
    }

    suspend fun onSuccess(completion: (res: T) -> Unit) : Resource<T> {
        if (status == Status.SUCCESS)
            withContext(Dispatchers.Main) { completion(data!!) }
        return this
    }

    suspend fun onSuccessWithoutResponse(completion: () -> Unit) : Resource<T?> {
        if (status != Status.ERROR)
            withContext(Dispatchers.Main) { completion() }
        return this
    }

    fun onError(completion: (errorMessage: ResponseError) -> Unit) : Resource<T> {
        if (status == Status.ERROR)
            completion(e!!)
        return this
    }
}

enum class Status {
    SUCCESS, SUCCESS_NULLABLE, ERROR
}

data class ServiceError(
    @get:JsonProperty("error")
    @param:JsonProperty("error")
    val error: String = "",
    @get:JsonProperty("timestamp")
    @param:JsonProperty("timestamp")
    val timestamp: String = "",
    @get:JsonProperty("path")
    @param:JsonProperty("path")
    val path: String = "",
    @get:JsonProperty("applicationCode")
    @param:JsonProperty("applicationCode")
    val applicationCode: String = ""
)

enum class ResponseError (
    val errorCode: Int,
    var errorMessage: String = "") : Serializable {

    EXPIRED_TOKEN(401),
    TIMEOUT(-200),
    UNAUTHORIZED(400),
    FORBIDDEN(403),
    UNKNOWN(-500)
}