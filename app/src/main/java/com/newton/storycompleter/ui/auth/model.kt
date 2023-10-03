package com.newton.storycompleter.ui.auth

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Failure(
        val e: Exception
    ): Response<Nothing>()
}

data class Profile(val email:String,val id: String,val name: String, val credit:Int)