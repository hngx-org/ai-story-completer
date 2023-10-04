package com.newton.storycompleter.ui.auth

import android.content.Context
import com.shegs.hng_auth_library.authlibrary.AuthLibrary
import com.shegs.hng_auth_library.model.AuthResponse
import com.shegs.hng_auth_library.model.LoginRequest
import com.shegs.hng_auth_library.model.LogoutResponse
import com.shegs.hng_auth_library.model.SignupRequest
import com.shegs.hng_auth_library.network.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(context: Context) {

    private val apiService = AuthLibrary.createAuthService()
    private val dataStoreRepository = AuthLibrary.createDataStoreRepository(context)

    suspend fun SignUp(name: String, email: String, password: String): Response<AuthResponse>  {

        val signupRepository = AuthLibrary.createSignupRepository(apiService)

        val signupRequest = SignupRequest(
            name = name,
            email = email,
            password = password,
            confirm_password = password
        )
        return when (val result: ApiResponse<AuthResponse> = signupRepository.signup(signupRequest)) {
         is ApiResponse.Success -> Response.Success(result.data)
         is ApiResponse.Error -> Response.Failure(result.message)
        }

    }

    suspend fun SignIn(email: String, password: String): Response<AuthResponse> {
        val loginRepository = AuthLibrary.createLoginRepository(apiService, dataStoreRepository)

        val loginRequest = LoginRequest(
            email = email,
            password = password
        )
        val result: ApiResponse<AuthResponse> = loginRepository.login(loginRequest)
       return when(result){
           is ApiResponse.Success -> Response.Success(result.data)
           is ApiResponse.Error -> Response.Failure(result.message)
       }
    }

    suspend fun Logout(): Response<LogoutResponse> {
        val logoutRepository = AuthLibrary.createLogoutRepository(apiService)
        val result: ApiResponse<LogoutResponse> = logoutRepository.logout()
        return when(result){
            is ApiResponse.Success -> Response.Success(result.data)
            is ApiResponse.Error -> Response.Failure(result.message)
        }

    }

    suspend fun userProfile(): Response<AuthResponse> {
        val profileRepository = AuthLibrary.createProfileRepository(apiService)
        val result: ApiResponse<AuthResponse> = profileRepository.profile()

        return when(result){
            is ApiResponse.Success -> Response.Success(result.data)
            is ApiResponse.Error -> Response.Failure(result.message)
        }
    }
}


