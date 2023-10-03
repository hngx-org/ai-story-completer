package com.newton.storycompleter.ui.auth

sealed class Response<out T> {

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Failure(
        val e: String
    ): Response<Nothing>()
}

data class Profile(val email:String,val id: String,val name: String, val credit:Int)