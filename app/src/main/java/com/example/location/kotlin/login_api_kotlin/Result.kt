package com.example.location.kotlin.login_api_kotlin


import retrofit2.HttpException
sealed class Result<out T : Any> {

    class Ok<out T : Any>(

        val value: T,
        override val response: okhttp3.Response

    ) : Result<T>(), ResponseResult {

        override fun toString() = "Result.Ok{value=$value, response=$response}"

    }

    class Error(
        override val exception: HttpException,

        override val response: okhttp3.Response

    ) : Result<Nothing>(), ErrorResult, ResponseResult {

        override fun toString() = "Result.Error{exception=$exception}"
    }

    class Exception(
        override val exception: Throwable
    ) : Result<Nothing>(), ErrorResult {

        override fun toString() = "Result.Exception{exception=$exception}"

    }
}

interface ResponseResult {
    val response: okhttp3.Response
}
interface ErrorResult {
    val exception: Throwable

}
