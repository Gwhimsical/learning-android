package com.example.iterativepractice.core.result

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    fun exceptionOrNull(): Exception? = when (this) {
        is Success -> null
        is Error -> exception
    }

    companion object {
        fun <T> success(data: T): ApiResult<T> = Success(data)
        fun error(exception: Exception): ApiResult<Nothing> = Error(exception)
    }
}